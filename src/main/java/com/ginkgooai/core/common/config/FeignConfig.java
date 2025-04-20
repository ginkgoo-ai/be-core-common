package com.ginkgooai.core.common.config;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ginkgooai.core.common.utils.ContextUtils;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.annotation.Resource;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: david
 * @date: 16:28 2025/2/11
 */

@Configuration
public class FeignConfig {

    private static final String TRACE_PARENT = "00-%s-%s-01";

    private static final String BASE_PARENT_ID = "0000000000000001";
    
    @Resource
    private Tracer tracer;

    @Bean
    public RequestInterceptor traceIdFeignInterceptor() {
        return template -> {
            // 1. Add trace information
            addTraceHeaders(template);

            // 2. Add Bearer token
            addAuthorizationHeader(template);

            // 3. Add Context
            addContextPropagation(template);
        };
    }


    /**
     * Add distributed tracing headers to the request
     * Format: 00-traceId-parentId-01
     */
    private void addTraceHeaders(feign.RequestTemplate template) {
        String traceId = tracer.currentSpan() != null ?
                tracer.currentSpan().context().traceIdString() : null;
        String parentId = tracer.currentSpan() != null ?
                tracer.currentSpan().context().parentIdString() : null;

        String traceParent = String.format(TRACE_PARENT,
                traceId,
                parentId == null ? BASE_PARENT_ID : parentId
        );
        template.header("traceparent", traceParent);
    }

    /**
     * Propagate Authorization header from the current request to Feign client
     * This ensures Bearer token is passed to downstream services
     */
    private void addAuthorizationHeader(feign.RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            String authorization = attributes.getRequest()
                    .getHeader(HttpHeaders.AUTHORIZATION);

            if (StringUtils.hasText(authorization)) {
                template.header(HttpHeaders.AUTHORIZATION, authorization);
			}
			else {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
					template.header(HttpHeaders.AUTHORIZATION, "Bearer " + oidcUser.getIdToken().getTokenValue());
				}
            }
        }
    }


    /**
     * Propagate context information from the current context to the Feign request.
     * This method iterates over all key-value pairs in the context and adds them as headers to the request.
     *
     * @param template The Feign request template to which the context headers will be added.
     */
    private void addContextPropagation(feign.RequestTemplate template) {
        if (ContextUtils.get() == null) {
            return;
        }

        ContextUtils.get().forEach((key, value) -> {
            template.header(key, value.toString());
        });
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new FeignProblemDetailErrorDecoder(objectMapper);
    }

	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}
}

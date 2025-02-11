package com.ginkgooai.core.common.config;

import brave.Tracer;
import feign.RequestInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceIdString() : null;
            String parentId = tracer.currentSpan() != null ? tracer.currentSpan().context().parentIdString() : null;

            String traceParent = String.format(TRACE_PARENT, traceId, parentId == null ? BASE_PARENT_ID : parentId);
            template.header("traceparent", traceParent);
        };

    }

}

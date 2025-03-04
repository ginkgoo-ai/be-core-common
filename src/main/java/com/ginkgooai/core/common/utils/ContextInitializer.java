package com.ginkgooai.core.common.utils;

import com.ginkgooai.core.common.constant.ContextsConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Enumeration;

@Slf4j
public class ContextInitializer implements ContextsConstant {

    private static final String HEADER_WORKSPACE_ID = "x-workspace-id";

    private static final String PREFIX = "t-";


    public static void load(HttpServletRequest request) {
        loadFromHeaders(request);
        loadFromSecurityContext();
    }


    public static void loadFromHeaders(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (header.startsWith(HEADER_WORKSPACE_ID)) {// 去掉前缀
                ContextUtils.set(ContextsConstant.WORKSPACE_ID, request.getHeader(header));
            }

            if (header.startsWith(PREFIX)) {
                ContextUtils.set(header, request.getHeader(header));
            }
        }
    }

    public static void loadFromSecurityContext() {
        if(SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null) {
            return;
        }

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ContextUtils.set(ContextsConstant.USER_ID, jwt.getClaimAsString("sub"));
        ContextUtils.set(ContextsConstant.USER_EMAIL, jwt.getClaimAsString("email"));
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ContextUtils.set(ContextsConstant.USER_ROLE, authorities);
    }


}

package com.ginkgooai.core.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class IpUtils {
    /**
     * Gets client IP address from request
     *
     * @param request The HTTP request
     * @return Client IP address as string
     */
    public String getClientIpAddress(HttpServletRequest request) {
        // Headers ordered by priority (highest first)
        List<String> IP_HEADERS = Arrays.asList(
            "True-Client-IP",     // Cloudflare True-Client-IP (highest priority)
            "CF-Connecting-IP",   // Alternative Cloudflare header
            "X-Forwarded-For",    // Standard proxy header
            "X-Real-IP",          // Nginx proxy/FastCGI
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        );

        for (String header : IP_HEADERS) {
            String ipFromHeader = request.getHeader(header);
            if (ipFromHeader != null && ipFromHeader.length() != 0
                && !"unknown".equalsIgnoreCase(ipFromHeader)) {
                // X-Forwarded-For might contain multiple IPs in format: client, proxy1, proxy2...
                // We want to extract the first one
                int commaIndex = ipFromHeader.indexOf(',');
                if (commaIndex != -1) {
                    return ipFromHeader.substring(0, commaIndex).trim();
                }
                return ipFromHeader.trim();
            }
        }

        return request.getRemoteAddr();
    }
}
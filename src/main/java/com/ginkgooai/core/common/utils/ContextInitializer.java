package com.ginkgooai.core.common.utils;

import com.ginkgooai.core.common.constant.ContextsConstant;
import com.nimbusds.jose.util.StandardCharset;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Enumeration;

@Slf4j
public class ContextInitializer implements ContextsConstant {

    private static final String HEADER_WORKSPACE_ID = "x-workspace-id";

    public static void loadFromHeaders(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (header.startsWith(HEADER_WORKSPACE_ID)) {// 去掉前缀
                ContextUtils.set(ContextsConstant.WORKSPACE_ID, request.getHeader(header));
            }
        }
    }

    public static void loadFromJWT(HttpServletRequest request, String secretKey) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authorizationHeader.substring(7);

        Claims claims = verifyToken(token, secretKey);

        request.setAttribute("userId", claims.getSubject());
    }

    public static Claims verifyToken(String token, String secretKey) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharset.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

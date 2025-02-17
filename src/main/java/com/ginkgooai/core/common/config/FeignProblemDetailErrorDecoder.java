package com.ginkgooai.core.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ginkgooai.core.common.exception.BaseRuntimeException;
import com.ginkgooai.core.common.exception.RemoteServiceException;
import com.ginkgooai.core.common.exception.ResourceNotFoundException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class FeignProblemDetailErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    public FeignProblemDetailErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // Read response body
            String responseBody = getResponseBody(response);
            
            // Parse problem detail from response
            ProblemDetail problemDetail = parseProblemDetail(responseBody);
            
            // Convert to custom exception based on type
            return convertToDomainException(problemDetail);
            
        } catch (Exception e) {
            log.error("Failed to decode feign error response", e);
            return new RemoteServiceException(
                "remote_service_error",
                "Remote Service Error",
                "Failed to process error response from remote service",
                HttpStatus.valueOf(response.status())
            );
        }
    }

    private String getResponseBody(Response response) throws IOException {
        if (response.body() == null) {
            return null;
        }
        return Util.toString(response.body().asReader(StandardCharsets.UTF_8));
    }

    private ProblemDetail parseProblemDetail(String responseBody) throws JsonProcessingException {
        if (responseBody == null || responseBody.isEmpty()) {
            throw new IllegalStateException("Empty response body");
        }
        return objectMapper.readValue(responseBody, ProblemDetail.class);
    }

    private BaseRuntimeException convertToDomainException(ProblemDetail problemDetail) {
        // Extract type from URI
        String type = extractTypeFromUri(problemDetail.getType());
        HttpStatus status = HttpStatus.valueOf(problemDetail.getStatus());

        // Map to specific exception based on type
        return switch (type) {
            case "resource_not_found" -> new ResourceNotFoundException(
                // Extract additional properties if needed
                (String) problemDetail.getProperties().get("resourceName"),
                (String) problemDetail.getProperties().get("fieldName"),
                problemDetail.getProperties().get("fieldValue")
            );
            // Add more cases for other exception types
            default -> new RemoteServiceException(
                type,
                problemDetail.getTitle(),
                problemDetail.getDetail(),
                status
            );
        };
    }

    private String extractTypeFromUri(URI typeUri) {
        String path = typeUri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
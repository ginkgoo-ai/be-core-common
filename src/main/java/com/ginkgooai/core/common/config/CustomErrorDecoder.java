package com.ginkgooai.core.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ginkgooai.core.common.exception.GinkgooRunTimeException;
import com.ginkgooai.core.common.exception.enums.CustomErrorEnum;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @author: david
 * @date: 16:26 2025/2/10
 */

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 解析器

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.body() == null) {
            return new GinkgooRunTimeException(CustomErrorEnum.SERVICE_NOT_AVAILABLE);
        }

        try {
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            return objectMapper.readValue(body, GinkgooRunTimeException.class);
        } catch (IOException e) {
            return new GinkgooRunTimeException(CustomErrorEnum.INVALID_RESPONSE_FORMAT);
        }

    }
}


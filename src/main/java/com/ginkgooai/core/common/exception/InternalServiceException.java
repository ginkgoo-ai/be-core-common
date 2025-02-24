package com.ginkgooai.core.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServiceException extends BaseRuntimeException {

    public InternalServiceException(String detail) {
        super(
                "error",
                "Internal Service Error",
                detail,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

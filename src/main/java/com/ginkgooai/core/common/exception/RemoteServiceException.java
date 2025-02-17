package com.ginkgooai.core.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RemoteServiceException extends BaseRuntimeException {
    public RemoteServiceException(String type, String title, String detail, HttpStatus status) {
        super(type, title, detail, status);
    }
}
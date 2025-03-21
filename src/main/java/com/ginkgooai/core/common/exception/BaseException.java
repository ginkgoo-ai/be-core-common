package com.ginkgooai.core.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception class that implements the ProblemDetailAware interface.
 * Provides common functionality for all custom exceptions in the application.
 */
@Getter
public abstract class BaseException extends Exception implements ProblemDetailAware {
    private final String type;
    private final String title;
    private final HttpStatus status;

    protected BaseException(String type, String title, String detail, HttpStatus status) {
        super(detail);
        this.type = type;
        this.title = title;
        this.status = status;
    }

    @Override
    public String getDetail() {
        return getMessage();
    }
}
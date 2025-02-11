package com.ginkgooai.core.common.exception;

import com.ginkgooai.core.common.exception.enums.CustomErrorEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: david
 * @date: 16:26 2025/2/10
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class GinkgooRunTimeException extends RuntimeException{

    public final Integer httpStatus;
    private final String statusCode;
    private final String message;

    public GinkgooRunTimeException(Integer httpStatus, String statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public GinkgooRunTimeException(CustomErrorEnum customError) {
        super(customError.message);
        this.statusCode = customError.statusCode;
        this.message = customError.message;
        this.httpStatus = customError.httpStatus;
    }
}

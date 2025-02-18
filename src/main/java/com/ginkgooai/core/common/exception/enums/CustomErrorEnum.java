package com.ginkgooai.core.common.exception.enums;

/**
 * @author: david
 * @date: 16:28 2025/2/10
 */
public enum CustomErrorEnum {
    SERVICE_NOT_AVAILABLE(503, "SERVICE-001", "The service is temporarily unavailable. Please try again later."),
    INVALID_RESPONSE_FORMAT(400, "SERVICE-002", "The response body could not be parsed."),


    EMAIL_SEND_EXCEPTION(500, "MESSAGING-001", "Email send exception."),

    UPLOADING_FILE_EXCEPTION(500, "STORAGE-001", "Error uploading file to R2."),
    OBTAINING_DOWNLOAD_LINK_EXCEPTION(500, "STORAGE-002", "Error generating pre signed URL."),

    ;
    public final Integer httpStatus;

    public final String statusCode;

    public final String message;

    CustomErrorEnum(String code, String message) {
        this.statusCode = code;
        this.message = message;
        this.httpStatus = 500;
    }

    CustomErrorEnum(Integer httpStatus, String code, String message) {
        this.statusCode = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
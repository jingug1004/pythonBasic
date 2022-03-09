package com.beauty.common;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * Error model for interacting with client.
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
public class ErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final ErrorCode resultCode;

    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorCode resultCode, HttpStatus status) {
        this.message = message;
        this.resultCode = resultCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    public static ErrorResponse of(final String message, final ErrorCode resultCode, HttpStatus status) {
        return new ErrorResponse(message, resultCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getResultCode() {
        return resultCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

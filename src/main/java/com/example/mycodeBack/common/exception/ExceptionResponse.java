package com.example.mycodeBack.common.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private int code;
    private String message;

    public ExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public static ExceptionResponse of(int code, String message) {
        return new ExceptionResponse(code, message);
    }
}

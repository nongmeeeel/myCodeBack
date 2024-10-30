package com.example.mycodeBack.common.exception;

import com.example.mycodeBack.common.exception.type.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final int code;
    private final String message;


    public CustomException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}

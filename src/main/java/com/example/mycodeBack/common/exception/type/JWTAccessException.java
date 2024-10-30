package com.example.mycodeBack.common.exception.type;

import com.example.mycodeBack.common.exception.CustomException;

public class JWTAccessException extends CustomException {

    public JWTAccessException(final ExceptionCode exceptionCode){
        super(exceptionCode);
    }
}

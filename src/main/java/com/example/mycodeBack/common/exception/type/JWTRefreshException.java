package com.example.mycodeBack.common.exception.type;

import com.example.mycodeBack.common.exception.CustomException;

public class JWTRefreshException extends CustomException {

    public JWTRefreshException(final ExceptionCode exceptionCode){
        super(exceptionCode);
    }
}

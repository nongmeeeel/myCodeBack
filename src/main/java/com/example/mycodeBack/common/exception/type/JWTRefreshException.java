package com.example.mycodeBack.common.exception.type;

import com.example.mycodeBack.common.exception.CustomException;
import com.example.mycodeBack.common.exception.ExceptionCode;

public class JWTRefreshException extends CustomException {

    public JWTRefreshException(final ExceptionCode exceptionCode){
        super(exceptionCode);
    }
}

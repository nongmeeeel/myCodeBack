package com.example.mycodeBack.common.exception.type;

import com.example.mycodeBack.common.exception.CustomException;
import com.example.mycodeBack.common.exception.ExceptionCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}

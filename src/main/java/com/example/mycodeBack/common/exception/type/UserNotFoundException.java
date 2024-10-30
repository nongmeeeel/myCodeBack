package com.example.mycodeBack.common.exception.type;

import com.example.mycodeBack.common.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}

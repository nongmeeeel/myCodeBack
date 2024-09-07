package com.example.mycodeBack.common.exception;

import com.example.mycodeBack.common.exception.type.JWTAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {

    // 인증 인가 에러
    @ExceptionHandler(JWTAccessException.class)
    public ResponseEntity<ExceptionResponse> handleJWTException(JWTAccessException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exceptionResponse);
    }

    // 런타임 에러 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, "NullPointerException");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException .class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(400, "IllegalArgumentException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ExceptionResponse> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(400, "IndexOutOfBoundsException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }



    // 런타임 기본 에러
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(JWTAccessException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, "RuntimeException");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    // 최상위 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, "Exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }
}

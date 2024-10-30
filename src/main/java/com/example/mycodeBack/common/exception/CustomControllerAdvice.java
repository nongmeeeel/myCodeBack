package com.example.mycodeBack.common.exception;

import com.example.mycodeBack.common.exception.type.JWTAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    // 인증 인가 에러
    @ExceptionHandler(JWTAccessException.class)
    public ResponseEntity<ExceptionResponse> handleJWTException(JWTAccessException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getCode(), e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exceptionResponse);
    }

    // 런타임 에러 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(400, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ExceptionResponse> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(400, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    // 런타임 기본 에러
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    // 최상위 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(500, e.getMessage());
        log.error("에러 내용 : ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }
}

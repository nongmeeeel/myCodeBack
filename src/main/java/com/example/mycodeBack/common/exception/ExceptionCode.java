package com.example.mycodeBack.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    // 인증,인가 예외 처리
    ACCESS_TOKEN_ERROR(9000, "엑세스 토큰 인증에 문제가 발생했습니다."),
    ACCESS_TOKEN_MALFORMED(9001, "엑세스 토큰 인증 형식이 올바르지 않습니다."),
    ACCESS_TOKEN_EXPIRED(9002, "엑세스 토큰 인증 유효기간이 만료되었습니다."),
    REFRESH_TOKEN_ERROR(9003, "리프레시 토큰 인증에 문제가 발생했습니다."),

    // 로그인 예외 처리
    LOGIN_FAILED(9000, "로그인에 실패하였습니다."),
    NOT_FOUND_USER_ID(9011, "해당 유저를 찾을 수 없습니다."),
    NOT_MACHED_USER_PW(9012, "비밀번호가 일치하지 않습니다.");

    // 예외 처리


    private final int code;
    private final String message;
}

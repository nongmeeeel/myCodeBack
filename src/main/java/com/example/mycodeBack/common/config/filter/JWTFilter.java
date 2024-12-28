package com.example.mycodeBack.common.config.filter;

import com.example.mycodeBack.common.config.auth.JWTUtil;
import com.example.mycodeBack.common.exception.type.JWTAccessException;
import com.example.mycodeBack.common.exception.type.JWTRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.ACCESS_TOKEN_ERROR;
import static com.example.mycodeBack.common.exception.type.ExceptionCode.REFRESH_TOKEN_ERROR;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JWT필터 작동");

        /* 로그인 요청의 경우 다음 필터(로그인 필터)로 진행 */
        if(request.getRequestURI().equals("/login") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        /* 1. 사용자 헤더에서 Refresh Token 추출 */
        String refreshToken = jwtUtil.getRefreshToken(request)
                .orElse(null);
        String accessToken = jwtUtil.getAccessToken(request)
                .orElse(null);

        if(refreshToken != null) {
            try {
                 jwtUtil.checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            } catch (Exception e) {
                throw new JWTRefreshException(REFRESH_TOKEN_ERROR);
            }
            return;
        }

        if(refreshToken == null) {
            try {
                jwtUtil.checkAccessTokenAndAuthentication(request, response, filterChain, accessToken);
            } catch (ExpiredJwtException e) {
                throw e;
            } catch (MalformedJwtException e) {
                throw e;
            } catch (Exception e) {
                throw new JWTAccessException(ACCESS_TOKEN_ERROR);
            }
            filterChain.doFilter(request, response);
        }


    }
}

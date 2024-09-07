package com.example.mycodeBack.common.config.filter;

import com.example.mycodeBack.common.exception.ExceptionResponse;
import com.example.mycodeBack.common.exception.type.JWTAccessException;
import com.example.mycodeBack.common.exception.type.JWTRefreshException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static com.example.mycodeBack.common.exception.ExceptionCode.*;

@RequiredArgsConstructor
public class JWTExceptionFilter implements Filter {

    private final ObjectMapper objectMapper;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            String errorResponse = objectMapper.writeValueAsString(new ExceptionResponse(ACCESS_TOKEN_EXPIRED));
            response.getWriter().write(errorResponse);

        } catch (MalformedJwtException e) {
            String errorResponse = objectMapper.writeValueAsString(new ExceptionResponse(ACCESS_TOKEN_MALFORMED));
            response.getWriter().write(errorResponse);

        } catch (JWTAccessException e) {
            String errorResponse = objectMapper.writeValueAsString(new ExceptionResponse(ACCESS_TOKEN_ERROR));
            response.getWriter().write(errorResponse);

        } catch (JWTRefreshException e) {
            String errorResponse = objectMapper.writeValueAsString(new ExceptionResponse(REFRESH_TOKEN_ERROR));
            response.getWriter().write(errorResponse);
        }
    }

    @Override
    public void destroy() {
    }
}

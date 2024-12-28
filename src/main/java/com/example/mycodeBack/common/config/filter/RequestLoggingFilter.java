package com.example.mycodeBack.common.config.filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*") // 모든 요청에 대해 필터를 적용
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 코드 (필요시)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("요청 URL: " + request.getRemoteAddr());

        // 요청을 계속해서 처리하도록 필터 체인 진행
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 정리 작업 (필요시)
    }
}
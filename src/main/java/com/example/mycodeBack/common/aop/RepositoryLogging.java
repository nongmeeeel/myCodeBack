package com.example.mycodeBack.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryLogging {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryLogging.class);

    // 모든 기능별 repository 클래스의 메서드 로깅
    @Before("execution(* com.example.mycodeBack..domain.repository..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("JPA 명: {}", methodName);
    }
}

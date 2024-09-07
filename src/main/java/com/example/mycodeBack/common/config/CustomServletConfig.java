package com.example.mycodeBack.common.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 별도의 설정을 하지 않으면 sprintSecurity의 CORS 설정에 통과된 것을 전부 받음

//@Configuration
//@Log4j2
//public class CustomServletConfig implements WebMvcConfigurer {
////    @Override
////    public void addFormatters(FormatterRegistry registry) {
////        log.info("------------------------");
////        log.info("addFormatters");
////        registry.addFormatter(new LocalDateFormatter());
////    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .maxAge(500)
//                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")
//                .allowedOrigins("*");
//    }
//}

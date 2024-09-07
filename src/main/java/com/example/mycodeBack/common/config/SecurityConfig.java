package com.example.mycodeBack.common.config;

import com.example.mycodeBack.common.config.filter.JWTExceptionFilter;
import com.example.mycodeBack.common.config.filter.JWTFilter;
import com.example.mycodeBack.common.config.auth.JWTUtil;
import com.example.mycodeBack.common.config.filter.LoginFilter;
import com.example.mycodeBack.member.MemberService;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Log4j2
@RequiredArgsConstructor
//@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    @Bean
    // filter를 커스텀 설정 한다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------------security config start---------------");

        // CSRF, Session, LoginForm, httpBasic 사용X - 토큰 기반 API 서버에서 필요 없음
        // JwtAuthenticationFilter 를 별도로 등록 (formLogin이 disable이라 기본 동작을 안하기 때문)
        http.csrf(config -> config.disable());
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(config -> config.disable());
        http.httpBasic(config -> config.disable());

        // CORS 설정 (어떤 요청을 허용할 것인가?)
        http.cors(config -> config.configurationSource(corsConfigurationSource()));

        // 요청별 인증,인가 설정
        http.authorizeHttpRequests(config ->
                config
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/vip/**").hasAnyRole("VIP", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
        );

        // 인증되지 않은 사용자에게 리디렉션 할 login 페이지 지정
//        http.formLogin(config -> {
//            config.loginPage("/api/member/login");
//            config.successHandler(new APILoginSuccessHandler());
//            config.failureHandler(new APILoginFailHandler());
//        });

        // login 요청이 왔을 경우 처리하는 필터 커스텀으로 만들어야함. (기본인 loginForm을 disable 했기 때문)
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper, memberService), UsernamePasswordAuthenticationFilter.class);

        // JWT 검증 및 SecurityContextHolder 객체 설정 로직 필요
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        // JWT 에러 발생 시 catch & handling 필터
        http.addFilterBefore(new JWTExceptionFilter(objectMapper), JWTFilter.class);

        // 필터에서 에러 났을 시 핸들링 하는 것 만들어야 함.
//        http.exceptionHandling(config -> {
//            config.accessDeniedHandler(new CustomAccessDeniedHandler());
//        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 처리 해주
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // CORS 세팅
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // UrlBasedCorsConfigurationSource 객체를 생성합니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 새로운 CorsConfiguration 객체를 생성합니다.
        CorsConfiguration config = new CorsConfiguration();

        // 모든 도메인 허용
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        // 모든 메서드 허용
        config.setAllowedMethods(Arrays.asList("*"));
        // 모든 헤더 허용
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Access-Token", "Refresh-Token"));
        // 모든 요청에 corsConfig 설정 등록
        source.registerCorsConfiguration("/**", config);
        // 이 CORS 설정 소스를 반환합니다.
        return source;
    }

}

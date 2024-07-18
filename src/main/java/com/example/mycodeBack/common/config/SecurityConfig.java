//package com.example.mycodeBack.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable() // CSRF 토큰 비활성화
////                .authorizeRequests()
////                .requestMatchers()
////                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
////                .and()
////                .permitAll() // 해당 경로들은 인증 없이 이용 가능
////                .anyRequest()
////                .authenticated() // 인증이 필요함 - 없는 경우 접근 제한 페이지를 보여줌
////                .and()
////                .formLogin() // 로그인 폼 설정
////                .loginPage("/auth/loginForm") // 로그인 경로 설정
////                .defaultSuccessUrl("/"); // 로그인 성공시 리다이렉션 경로 설정
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//        return http.build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 로컬 React에서 오는 요청은 허용한다.
////        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        // 모든 경로 허용
//        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList(
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
//                "Content-Type", "Authorization", "X-Requested-With", "Access-Token", "Refresh-Token"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Token", "Refresh-Token"));
//        // 모든 요청 url 패턴에 대해 위의 설정을 적용한다.
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//}
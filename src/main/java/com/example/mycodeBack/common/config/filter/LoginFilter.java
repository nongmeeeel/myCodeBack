package com.example.mycodeBack.common.config.filter;

import com.example.mycodeBack.common.config.auth.CustomUserDetails;
import com.example.mycodeBack.common.config.auth.JWTUtil;
import com.example.mycodeBack.common.exception.ExceptionResponse;
import com.example.mycodeBack.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.*;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 필터가 기본적으로 있음.
// 원래는 formLogin 에서 userName,PW 전송하면 기본으로 동작함.
// 하지만 formLogin을 disable 했기 때문에 기본 동작 안함

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    // Login 검증 및 인증객체 설정


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // Content Type : "application/json" 확인 (안정성)
        if(request.getContentType() == null || !request.getContentType().equals("application/json")) {
            throw new AuthenticationServiceException("Content-Type이 Json이 아니야 ~!");
        }

        try {
            // Request Body : Json 형식 USERNAME, PASSWORD 읽어오기
            String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            Map<String, String> bodyMap = objectMapper.readValue(body, Map.class);

            String username = bodyMap.get("username");
            String password = bodyMap.get("password");
            String loginType = bodyMap.get("loginType");
            String nickname = bodyMap.get("nickname");

            // 소셜 로그인('K' - 카카오)인 경우 자동 회원가입 진행
            if (loginType.equals("K")) {
                memberService.checkSignUpKakaoEmail(username, password, loginType, nickname);
            }

            // 인증매니저를 통해 id,pw 검증 진행
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // Login 검증 성공 시 -> 인증객체의 정보로 Token 발행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        Long id = customUserDetails.getId();
        log.info("username : {}", username);
        log.info("role : {}", role);

        String accessToken = jwtUtil.createAccessToken(username, role, id);
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("발급된 accessToken : {}", accessToken);
        log.info("발급된 refreshToken : {}", refreshToken);

        memberService.updateRefreshToken(username, refreshToken);

        response.setHeader("Access-Token", accessToken);
        response.setHeader("Refresh-Token", refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // Login 검증 실패 시 -> 에러 반환
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패 : {}", failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        if (failed.getMessage().equals("해당 유저를 찾을 수 없습니다.")) {
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(NOT_FOUND_USER_ID)));
        } else if (failed.getMessage().equals("자격 증명에 실패하였습니다.")) {
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(NOT_MACHED_USER_PW)));
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(LOGIN_FAILED)));
        }
    }
}

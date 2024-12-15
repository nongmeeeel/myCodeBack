package com.example.mycodeBack.common.config.auth;

import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class JWTUtil {
    public JWTUtil(@Value("${jwt.secretKey}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    @Value("${jwt.access.expiration}")
    private Long ACCESS_TOKEN_EXPIRED_MS;
    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_TOKEN_EXPIRED_MS;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer ";
    private SecretKey secretKey;

    @Autowired
    private MemberRepository memberRepository;


    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createAccessToken(String username, String role, Long id) {
        return BEARER + Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("id", id)
                .subject(ACCESS_TOKEN_SUBJECT)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_MS))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken() {
        return BEARER + Jwts.builder()
                .subject(REFRESH_TOKEN_SUBJECT)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_MS))
                .signWith(secretKey)
                .compact();
    }

    public Optional<String> getAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Access-Token"))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Refresh-Token"))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String accessToken) throws ServletException, IOException {
        Claims claims = validAndParseToken(accessToken);
        String username = claims.get("username").toString();
        String role = claims.get("role").toString();
        Integer intId = (Integer) claims.get("id");
        Long id = intId.longValue();
        saveAuthentication(username, role, id);
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        validAndParseToken(refreshToken);
        Member member = memberRepository.findByRefreshToken(BEARER + refreshToken);

        String reIssuedRefreshToken = reIssuedRefreshToken(member);
        String accessToken = createAccessToken(member.getEmail(), member.getRole().name(), member.getId());

        response.setHeader("Access-Token", accessToken);
        response.setHeader("Refresh-Token", reIssuedRefreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public Claims validAndParseToken(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String reIssuedRefreshToken(Member member) {
        String reIssuedRefreshToken = createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }



    private Optional<String> getMemberId(Claims claims) {
        try {
            return Optional.of(claims.get("username").toString());
        } catch (Exception e) {
            log.error("Access Token이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void saveAuthentication(String username, String role, Long id) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        CustomUser customUser = CustomUser.of(username,role,id);

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

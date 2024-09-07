package com.example.mycodeBack.member.domain;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {
    GUEST,
    USER,
    VIP,
    ADMIN;

    @Override
    public String getAuthority() {
        return name(); // 권한을 문자열로 반환
    }
}

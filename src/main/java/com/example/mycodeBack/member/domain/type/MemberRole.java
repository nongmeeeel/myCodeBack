package com.example.mycodeBack.member.domain.type;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {
    GUEST,
    MEMBER,
    VIP,
    ADMIN;

    @Override
    public String getAuthority() {
        return name(); // 권한을 문자열로 반환
    }
}

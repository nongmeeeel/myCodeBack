package com.example.mycodeBack.common.config.audit;

import com.example.mycodeBack.common.config.auth.CustomUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        } else {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            // 로그인된 사용자의 이름 또는 ID를 반환
            return Optional.of(customUser.getEmail());
        }

    }
}

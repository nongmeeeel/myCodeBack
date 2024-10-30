package com.example.mycodeBack.common.config.auth;

import com.example.mycodeBack.common.exception.type.UserNotFoundException;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.NOT_FOUND_USER_ID;

// /login 경로로 요청 오면 디폴트로 실행 됨
@Service
@RequiredArgsConstructor
public class CusotmUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

        return new CustomUserDetails(member);
    }
}

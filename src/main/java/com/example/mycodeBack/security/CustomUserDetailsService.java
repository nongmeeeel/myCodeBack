//package com.example.mycodeBack.security;
//
//import com.example.mycodeBack.member.domain.Member;
//import com.example.mycodeBack.member.domain.repository.MemberRepository;
//import com.example.mycodeBack.common.config.auth.MemberDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//@Log4j2
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        log.info("------------입력된 유저 아이디--------------");
//        log.info("username : {}", username);
//
//        Member member = memberRepository.getWithRoles(username);
//
//        if(member == null) {
//            throw new UsernameNotFoundException("찾을 수 없옹");
//        }
//
//        MemberDTO memberDTO = new MemberDTO(
//                member.getEmail()
//                ,member.getPw()
//                ,member.getKakaoNickname()
//                ,member.getRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
//                ,member.getKakaoId()
//        );
//
//        log.info(memberDTO);
//
//        return memberDTO;
//    }
//}

package com.example.mycodeBack.common;

import com.example.mycodeBack.common.exception.type.UserNotFoundException;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.example.mycodeBack.common.exception.ExceptionCode.NOT_FOUND_USER_ID;

@Component
public class UserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MemberRepository memberRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String userId = request.getHeader("KAKAO_ID");
//        if (userId != null) {
//            return memberRepository.findByKakaoId(userId)
//                    .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
//        } else {
            return null;
//        }
    }
}

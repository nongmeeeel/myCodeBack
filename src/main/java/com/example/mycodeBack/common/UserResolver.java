package com.example.mycodeBack.common;

import com.example.mycodeBack.common.exception.UserNotFoundException;
import com.example.mycodeBack.user.UserService;
import com.example.mycodeBack.user.domain.User;
import com.example.mycodeBack.user.domain.repository.UserRepository;
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

@Component
public class UserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String userId = request.getHeader("KAKAO_ID");
        if (userId != null) {
            return userRepository.findByKakaoId(userId)
                    .orElseThrow(() -> new UserNotFoundException("가입되지 않은 카카오ID입니다. " + userId));
        } else {
            return null;
        }
    }
}

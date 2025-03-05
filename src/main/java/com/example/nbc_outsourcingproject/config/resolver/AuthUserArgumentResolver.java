package com.example.nbc_outsourcingproject.config.resolver;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.auth.exception.AuthException;
import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthentication = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        if (hasAuthentication != isAuthUserType) {
            throw new AuthException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }

        return hasAuthentication;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ){
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        UserRole userRole = UserRole.of((String) request.getAttribute("userRole"));

        return new AuthUser(userId, email, userRole);
    }

}

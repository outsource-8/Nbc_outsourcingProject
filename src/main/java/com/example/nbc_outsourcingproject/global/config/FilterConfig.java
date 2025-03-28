package com.example.nbc_outsourcingproject.global.config;

import com.example.nbc_outsourcingproject.domain.token.repository.ReFreshTokenRepository;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.global.jwt.JwtFilter;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private final ReFreshTokenRepository reFreshTokenRepository;
    private final UserRepository userRepository;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtUtil, reFreshTokenRepository, userRepository));
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}

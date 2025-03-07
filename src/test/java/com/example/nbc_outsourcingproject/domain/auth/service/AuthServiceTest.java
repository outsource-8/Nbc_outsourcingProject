package com.example.nbc_outsourcingproject.domain.auth.service;

import com.example.nbc_outsourcingproject.domain.auth.dto.request.LoginRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.SignupRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.token.entity.ReFreshToken;
import com.example.nbc_outsourcingproject.domain.token.repository.ReFreshTokenRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ReFreshTokenRepository reFreshTokenRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void 회원가입을_할_수_있다() {
        // given
        SignupRequest signupRequest = new SignupRequest(
                "test@email.com",
                "Test1234",
                "nickname",
                "Seoul",
                "USER"
        );
        User user = new User(
                signupRequest.getEmail(),
                "password",
                signupRequest.getNickname(),
                signupRequest.getAddress(),
                UserRole.USER);

        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(false);
        given(passwordEncoder.encode(signupRequest.getPassword())).willReturn("password");
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        authService.signup(signupRequest);
        // then
        then(userRepository).should().save(any(User.class));
//        assertThat()
    }
}


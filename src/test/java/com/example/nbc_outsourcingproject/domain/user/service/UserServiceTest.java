package com.example.nbc_outsourcingproject.domain.user.service;

import com.example.nbc_outsourcingproject.domain.auth.dto.request.SignupRequest;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입에_성공할수있을까() {
        // given
        SignupRequest request = new SignupRequest(
                "test@email.com",
                "password123",
                "nickname",
                "Seoul",
                "USER"
        );
        // when

        // then
    }

}
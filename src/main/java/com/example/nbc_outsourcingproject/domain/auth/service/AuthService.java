package com.example.nbc_outsourcingproject.domain.auth.service;

import com.example.nbc_outsourcingproject.config.resolver.PasswordEncoder;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.LoginRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.SignupRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.SignupResponse;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.auth.exception.AuthException;
import com.example.nbc_outsourcingproject.domain.common.exception.details.InvalidRequestException;
import com.example.nbc_outsourcingproject.config.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.domain.token.entity.ReFreshToken;
import com.example.nbc_outsourcingproject.domain.token.repository.ReFreshTokenRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ReFreshTokenRepository reFreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidRequestException("이미 사용중인 이메일입니다");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User user = new User(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getNickname(),
                signupRequest.getAddress(),
                userRole
        );
        User savedUser = userRepository.save(user);

        String bearerToken = jwtUtil.createAccessToken(savedUser.getId(), savedUser.getEmail(),userRole);

        return new SignupResponse(bearerToken);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse servletResponse) {
        log.info("AuthService::Login::Call");
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다");
        }
        String access = jwtUtil.createAccessToken(user.getId(), user.getEmail(),user.getUserRole());
        String refresh = jwtUtil.createRefreshToken(user.getId());


        ReFreshToken reFreshToken = ReFreshToken.builder()
                        .userId(user.getId())
                                .refreshToken(refresh)
                                        .createdAt(LocalDateTime.now())
                                                .build();

        log.info(reFreshToken.toString());
        reFreshTokenRepository.save(reFreshToken);


        servletResponse.setHeader("Authorization", access);

        setTokenToCookie(refresh, servletResponse);
//        String bearerToken = jwtUtil.createAccessToken(auth.getId(), auth.getEmail(),auth.getUserRole());
        return new LoginResponse(refresh);
    }

    private void setTokenToCookie(String bearerToken, HttpServletResponse servletResponse) {
        String token = jwtUtil.substringToken(bearerToken);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
    }
}

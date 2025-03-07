package com.example.nbc_outsourcingproject.domain.auth.service;

import com.example.nbc_outsourcingproject.global.exception.auth.*;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;
import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.LoginRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.SignupRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.SignupResponse;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
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
    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvaildEmailException();
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

        return;
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse servletResponse) {
        log.info("AuthService::Login::Call");
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                NotFoundUserException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        String access = jwtUtil.createAccessToken(user.getId(), user.getEmail(),user.getUserRole());
        String refresh = jwtUtil.createRefreshToken(String.valueOf(user.getId()));

        reFreshTokenRepository.save(new ReFreshToken(jwtUtil.substringToken(refresh), user.getId()));

        servletResponse.setHeader("Authorization", access);

        setTokenToCookie(refresh, servletResponse);

        return new LoginResponse(access, refresh);
    }

    private void setTokenToCookie(String bearerToken, HttpServletResponse servletResponse) {
        String token = jwtUtil.substringToken(bearerToken);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
    }
}

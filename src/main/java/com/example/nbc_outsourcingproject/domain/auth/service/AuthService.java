package com.example.nbc_outsourcingproject.domain.auth.service;

import com.example.nbc_outsourcingproject.config.PasswordEncoder;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.LoginRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.request.SignupRequest;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.SignupResponse;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.auth.exception.AuthException;
import com.example.nbc_outsourcingproject.domain.common.exception.InvalidRequestException;
import com.example.nbc_outsourcingproject.config.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

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
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new InvalidRequestException("가입되지 않은 유저입니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다");
        }
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(),user.getUserRole());
        String refreshToken = jwtUtil.createRefreshToken(user.getId());

        servletResponse.setHeader("Authorization", accessToken);

        setTokenToCookie(refreshToken, servletResponse);
//        String bearerToken = jwtUtil.createAccessToken(auth.getId(), auth.getEmail(),auth.getUserRole());
        return new LoginResponse(refreshToken);
    }

    private void setTokenToCookie(String bearerToken, HttpServletResponse servletResponse) {
        String token = jwtUtil.substringToken(bearerToken);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
    }
//    @Transactional
//    public void updateEmail(long userId, updateEmailRequest updateEmailRequest) {
//        Auth auth = authRepository.findById(userId).orElseThrow(
//                ()-> new InvalidRequestException("해당 유저를 찾을 수 없습니다")
//        );
//
//        auth.updateEmail()
//    }

//    @Transactional
//    public void updatePassword(String email, updatePasswordRequest updatePasswordRequest) {
//        System.out.println("여기 넘어가는지 확인");
//        Auth auth = authRepository.findByEmail(email).orElseThrow(
//                () -> new InvalidRequestException("해당 유저를 찾을수 없습니다")
//        );
//
//        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), auth.getPassword())) {
//            throw new InvalidRequestException("잘못된 비밀번호 입니다");
//        }
//
//        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), auth.getPassword())) {
//            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 달라야합니다");
//        }
//
//        auth.updatePassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
//    }


}

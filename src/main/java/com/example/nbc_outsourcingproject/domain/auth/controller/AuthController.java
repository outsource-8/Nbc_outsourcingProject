package com.example.nbc_outsourcingproject.domain.auth.controller;

import com.example.nbc_outsourcingproject.domain.auth.dto.request.*;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.SignupResponse;
import com.example.nbc_outsourcingproject.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public SignupResponse signup (@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/auth/login")
    public LoginResponse login (@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return authService.login(loginRequest, httpServletResponse);
    }

//    @PatchMapping("/email")
//    public void updateEamil(@Auth AuthUser authUser, @Valid @RequestBody updateEmailRequest updateEmailRequest) {
//        authService.updateEamil(authUser.getId(), updateEmailRequest);
//    }
//
//    @PatchMapping("/nickname")
//    public void updateNickname(@Auth AuthUser authUser, @Valid @RequestBody updateNicknameRequest updateNicknameRequest) {
//        authService.updateNickname(authUser.getId(), updateNicknameRequest);
//    }

//    @PatchMapping("/user/password")
//    public void updatePassword(@Auth AuthUser authUser, @Valid@RequestBody updatePasswordRequest updatePasswordRequest) {
//        authService.updatePassword(authUser.getEmail(), updatePasswordRequest);
//    }

}

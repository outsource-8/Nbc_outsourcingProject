package com.example.nbc_outsourcingproject.domain.auth.controller;

import com.example.nbc_outsourcingproject.domain.auth.dto.request.*;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.LoginResponse;
import com.example.nbc_outsourcingproject.domain.auth.dto.response.SignupResponse;
import com.example.nbc_outsourcingproject.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입 / 로그인 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입")
    public void signup (@Valid @RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인")
    public LoginResponse login (@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return authService.login(loginRequest, httpServletResponse);
    }


}

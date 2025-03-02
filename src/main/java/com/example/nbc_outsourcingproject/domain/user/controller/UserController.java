package com.example.nbc_outsourcingproject.domain.user.controller;

import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.user.dto.request.UpdatePassword;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/users/password")
    public void updatePassword(@Auth AuthUser authUser, @Valid @RequestBody UpdatePassword updatePassword) {
        userService.updatePassword(authUser.getId(),updatePassword);
    }
}

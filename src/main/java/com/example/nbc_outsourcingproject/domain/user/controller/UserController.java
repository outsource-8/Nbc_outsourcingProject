package com.example.nbc_outsourcingproject.domain.user.controller;

import com.example.nbc_outsourcingproject.domain.user.dto.request.DeleteUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.user.dto.request.UpdatePassword;
import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/user/password")
    public void updatePassword(@Auth AuthUser authUser, @Valid @RequestBody UpdatePassword updatePassword) {
        userService.updatePassword(authUser.getId(), updatePassword);
    }

    @PostMapping("/user/delete")
    public void deleteUser(@Auth AuthUser authUser, @Valid @RequestBody DeleteUser deleteUser) {
        userService.deleteUser(authUser.getId(), deleteUser);
    }
}

package com.example.nbc_outsourcingproject.domain.user.controller;

import com.example.nbc_outsourcingproject.domain.user.dto.request.DeleteUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.user.dto.request.UpdatePassword;
import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 관리 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/user/password")
    @Operation(summary = "비밀번호 수정")
    public void updatePassword(@Auth AuthUser authUser, @Valid @RequestBody UpdatePassword updatePassword) {
        userService.updatePassword(authUser.getId(), updatePassword);
    }

    @PostMapping("/user/delete")
    @Operation(summary = "유저 삭제")
    public void deleteUser(@Auth AuthUser authUser, @Valid @RequestBody DeleteUser deleteUser) {
        userService.deleteUser(authUser.getId(), deleteUser);
    }
}

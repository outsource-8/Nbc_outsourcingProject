package com.example.nbc_outsourcingproject.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePassword {

    @NotBlank
    private String oldPassword;
    @NotBlank
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A-Z].*).{8,}", message = "새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
    private String newPassword;
}

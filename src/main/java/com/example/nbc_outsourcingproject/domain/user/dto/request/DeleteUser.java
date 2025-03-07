package com.example.nbc_outsourcingproject.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUser {

    @NotBlank
    private String password;
}

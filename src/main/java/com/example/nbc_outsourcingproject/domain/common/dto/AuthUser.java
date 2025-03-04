package com.example.nbc_outsourcingproject.domain.common.dto;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public AuthUser(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }
}
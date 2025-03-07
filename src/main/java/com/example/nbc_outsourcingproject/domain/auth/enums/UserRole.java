package com.example.nbc_outsourcingproject.domain.auth.enums;

import com.example.nbc_outsourcingproject.global.exception.auth.InvalidRoleException;

import java.util.Arrays;

public enum UserRole {
    OWNER, USER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(InvalidRoleException::new);
    }
}

package com.example.nbc_outsourcingproject.domain.auth.enums;

import java.util.Arrays;

public enum UserRole {
    BUSINESS, USER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("유호하지 않은 권한입니다"));
    }
}

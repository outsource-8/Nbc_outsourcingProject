package com.example.nbc_outsourcingproject.domain.auth.dto.response;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import lombok.Getter;

@Getter
public class SignupResponse {

    private final String bearerToken;

    public SignupResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}

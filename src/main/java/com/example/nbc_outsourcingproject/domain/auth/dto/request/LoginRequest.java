package com.example.nbc_outsourcingproject.domain.auth.dto.request;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}

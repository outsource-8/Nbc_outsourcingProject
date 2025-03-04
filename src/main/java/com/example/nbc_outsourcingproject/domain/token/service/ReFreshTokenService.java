package com.example.nbc_outsourcingproject.domain.token.service;

import com.example.nbc_outsourcingproject.domain.token.entity.ReFreshToken;
import com.example.nbc_outsourcingproject.domain.token.repository.ReFreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// DB에 리프레시 토큰이 있는지 확인
public class ReFreshTokenService {

    private final ReFreshTokenRepository refreshTokenRepository;

    public ReFreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Invalid refresh token"));
    }
}

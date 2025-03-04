package com.example.nbc_outsourcingproject.domain.token.repository;

import com.example.nbc_outsourcingproject.domain.token.entity.ReFreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReFreshTokenRepository extends JpaRepository<ReFreshToken, Long> {
    Optional<ReFreshToken> findByUserId(Long userId);
    Optional<ReFreshToken> findByRefreshToken(String refreshToken);
    Boolean existsByRefreshToken(String refreshToken);
}

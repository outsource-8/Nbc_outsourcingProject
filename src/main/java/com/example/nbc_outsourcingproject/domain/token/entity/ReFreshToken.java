package com.example.nbc_outsourcingproject.domain.token.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refreshToken")
// 리프레시 토큰을 DB에 저장하기 위해 엔티티 생성
public class ReFreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "userId", nullable = false, updatable = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false, length = 1000)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @TimeToLive
    private Long expiration;

    public ReFreshToken(Long id, String refreshToken, LocalDateTime createdAt, Long expiration) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.expiration = expiration;
    }

    public ReFreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}

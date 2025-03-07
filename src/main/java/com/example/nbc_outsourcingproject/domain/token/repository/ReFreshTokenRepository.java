package com.example.nbc_outsourcingproject.domain.token.repository;


import com.example.nbc_outsourcingproject.domain.token.entity.ReFreshToken;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class ReFreshTokenRepository {
    private JwtUtil jwtUtil;

    private RedisTemplate redisTemplate;

    public ReFreshTokenRepository(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final ReFreshToken reFreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(reFreshToken.getRefreshToken(), reFreshToken.getUserId().toString());
        redisTemplate.expire(reFreshToken.getRefreshToken(), 600L, TimeUnit.SECONDS);
        System.out.println(reFreshToken.getRefreshToken());
    }

    public Optional<ReFreshToken> findById(final String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long userId = valueOperations.get(refreshToken);

        if (Objects.isNull(userId)) {
            return Optional.empty();
        }

        return Optional.of(new ReFreshToken(refreshToken, userId));
    }
}
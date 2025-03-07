package com.example.nbc_outsourcingproject.global.jwt;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.global.exception.auth.NotFoundTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 10 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(Long userId, String email, UserRole userRole) {
        Date date = new Date();

        String accesstoken = BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("userRole", userRole.name())
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
        return accesstoken;
    }


    public String createRefreshToken(String userId) {
        Date date = new Date();
        String refresh = BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
        return refresh;
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            // BEARER_PREFIX 이후의 문자열을 자르고, trim()을 적용해서 공백을 제거합니다.
            String removeToken = tokenValue.substring(BEARER_PREFIX.length()).trim();
            return removeToken;
        }
        throw new NotFoundTokenException();

    }

    public Claims extractToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    
}


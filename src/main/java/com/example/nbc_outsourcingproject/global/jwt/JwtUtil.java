package com.example.nbc_outsourcingproject.global.jwt;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.global.exception.auth.ServerException;
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
    private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_TIME = 30 * 60 * 1000L * 24 * 7;

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

        String token = BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("userRole", userRole.name())
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

//        log.info("Access Token 생성: {}", token);
//
        return token;
    }


    public String createRefreshToken(Long userId) {
        Date date = new Date();
        String refresh = BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();

//        log.info("refresh token 생성: {}", refresh);
        return refresh;
    }

//    public String substringToken(String tokenValue) {
////        log.info("로그인시 생성되는 토큰: {}", tokenValue);
//        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
////            log.info("토큰에 bearer가 있는지 확인: {}", tokenValue);
//            String removeToken = tokenValue.substring(7);
////            log.info("섭스트링 지운값 확인: {}",removeToken);
//            return removeToken;
//        }

    /// /        log.info("안지워졋다면 : {}", tokenValue);
//        throw new ServerException("Not Found Token");
//
//    }

    public String substringToken(String tokenValue) {
        log.info("로그인시 생성되는 토큰: {}", tokenValue);
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            log.info("토큰에 bearer가 있는지 확인: {}", tokenValue);
            // BEARER_PREFIX 이후의 문자열을 자르고, trim()을 적용해서 공백을 제거합니다.
            String removeToken = tokenValue.substring(BEARER_PREFIX.length()).trim();
            log.info("섭스트링 지운값 확인: {}", removeToken);
            return removeToken;
        }
        log.info("안지워졋다면 : {}", tokenValue);
        throw new ServerException("Not Found Token");
    }

    public Claims extractToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        Claims claims = extractToken(token);
        return claims.get("userId", Long.class);
    }
}


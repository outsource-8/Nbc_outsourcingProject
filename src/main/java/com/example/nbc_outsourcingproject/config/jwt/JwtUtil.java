package com.example.nbc_outsourcingproject.config.jwt;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.common.exception.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_TIME = 30 * 60 * 1000L * 24 * 7 ;

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

        log.info("Access Token 생성: {}", token);
//
        return token;
    }


    public String createRefreshToken(Long userId, String email, UserRole userRole) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("userRole", userRole.name())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String substringToken(String tokenValue) {
        log.info("로그인시 생성되는 토큰: {}", tokenValue);
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            log.info("토큰에 bearer가 있는지 확인: {}", tokenValue);
            String removetToken = tokenValue.substring(7);
            log.info("섭스트링 지운값 확인: {}",removetToken);
            return removetToken;
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
}


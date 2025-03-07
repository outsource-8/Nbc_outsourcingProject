package com.example.nbc_outsourcingproject.domain.token.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;


@Getter
@AllArgsConstructor
// 리프레시 토큰을 DB에 저장하기 위해 엔티티 생성
public class ReFreshToken {

    @Id
    private String refreshToken;
    private Long userId;
}
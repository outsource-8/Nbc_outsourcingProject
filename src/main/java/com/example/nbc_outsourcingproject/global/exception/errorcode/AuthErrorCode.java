package com.example.nbc_outsourcingproject.global.exception.errorcode;

import com.example.nbc_outsourcingproject.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다"),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND,"존재하지 않는 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유요하지 않은 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"지원하지 않는 토큰입니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST,"유효하지 않은 권한입니다."),
    AUTH(HttpStatus.UNAUTHORIZED,"@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");




    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

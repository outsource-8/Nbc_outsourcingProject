package com.example.nbc_outsourcingproject.global.exception.errorcode;

import com.example.nbc_outsourcingproject.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    NEWPASSWORD_DIFFERENT_OLDPASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호는 이전 비밀번호와 달라야합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALIDATE_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
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

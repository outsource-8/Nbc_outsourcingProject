package com.example.nbc_outsourcingproject.domain.user.exception;

import com.example.nbc_outsourcingproject.domain.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),;

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

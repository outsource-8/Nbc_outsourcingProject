package com.example.nbc_outsourcingproject.domain.menuoption.exception;

import com.example.nbc_outsourcingproject.domain.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MenuOptionErrorCode implements ErrorCode {
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MenuOptionErrorCode(HttpStatus httpStatus, String message) {
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

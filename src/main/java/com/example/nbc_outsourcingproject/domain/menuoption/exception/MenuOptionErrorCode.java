package com.example.nbc_outsourcingproject.domain.menuoption.exception;

import com.example.nbc_outsourcingproject.domain.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MenuOptionErrorCode implements ErrorCode {
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 옵션을 찾을 수 없습니다.");

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

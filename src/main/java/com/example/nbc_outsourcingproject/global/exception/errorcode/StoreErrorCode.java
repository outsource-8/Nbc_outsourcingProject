package com.example.nbc_outsourcingproject.global.exception.errorcode;

import com.example.nbc_outsourcingproject.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum StoreErrorCode implements  ErrorCode {
    MAX_STORE_CREATION(HttpStatus.BAD_REQUEST, "가게는 최대 3개까지 생성할 수 있습니다."),
    STORE_NOT_FOUND (HttpStatus.BAD_REQUEST, "가게를 찾을수 없습니다."),
    INVALID_STORE_OWNER(HttpStatus.FORBIDDEN, "가게에 접근할 수 있는 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    StoreErrorCode(HttpStatus httpStatus, String message) {
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

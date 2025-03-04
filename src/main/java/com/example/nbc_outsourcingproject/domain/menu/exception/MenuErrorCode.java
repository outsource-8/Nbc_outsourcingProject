package com.example.nbc_outsourcingproject.domain.menu.exception;

import com.example.nbc_outsourcingproject.domain.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MenuErrorCode implements ErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다."),
    DUPLICATE_MENU(HttpStatus.BAD_REQUEST, "이미 존재하는 메뉴 입니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    INVALID_STORE_MENU(HttpStatus.BAD_REQUEST, "가게에 존재하는 메뉴가 아닙니다."),
    INVALID_STORE_OWNER(HttpStatus.FORBIDDEN, "가게에 접근할 수 있는 권한이 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MenuErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


    public String getName(){
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

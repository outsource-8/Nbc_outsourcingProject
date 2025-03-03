package com.example.nbc_outsourcingproject.domain.menu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MenuException extends RuntimeException{
    private HttpStatus httpStatus;
    private MenuExceptionCode menuExceptionCode;

    public MenuException(MenuExceptionCode menuExceptionCode) {
        super(menuExceptionCode.getMessage());
        this.menuExceptionCode = menuExceptionCode;
        this.httpStatus = menuExceptionCode.getHttpStatus();
    }
}

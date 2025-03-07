package com.example.nbc_outsourcingproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OutsourcingException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public OutsourcingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
    }
}

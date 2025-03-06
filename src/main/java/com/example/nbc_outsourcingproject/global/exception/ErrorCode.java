package com.example.nbc_outsourcingproject.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getName();
    HttpStatus getHttpStatus();
    String getMessage();
}

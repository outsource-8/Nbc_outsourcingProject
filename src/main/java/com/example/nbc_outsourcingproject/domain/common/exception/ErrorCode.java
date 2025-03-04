package com.example.nbc_outsourcingproject.domain.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getName();
    HttpStatus getHttpStatus();
    String getMessage();
}

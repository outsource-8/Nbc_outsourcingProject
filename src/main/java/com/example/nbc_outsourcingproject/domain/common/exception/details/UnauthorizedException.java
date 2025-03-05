package com.example.nbc_outsourcingproject.domain.common.exception.details;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

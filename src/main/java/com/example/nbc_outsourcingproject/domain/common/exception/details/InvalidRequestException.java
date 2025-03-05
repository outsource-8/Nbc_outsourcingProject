package com.example.nbc_outsourcingproject.domain.common.exception.details;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}

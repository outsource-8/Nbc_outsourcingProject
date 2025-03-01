package com.example.nbc_outsourcingproject.store.common.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String message){
        super(message);
    }
}

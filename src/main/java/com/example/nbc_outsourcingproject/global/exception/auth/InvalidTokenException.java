package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class InvalidTokenException extends OutsourcingException {
    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_TOKEN);
    }
}

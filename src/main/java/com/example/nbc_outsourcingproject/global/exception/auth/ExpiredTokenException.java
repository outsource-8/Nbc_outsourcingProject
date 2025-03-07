package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class ExpiredTokenException extends OutsourcingException {
    public ExpiredTokenException() {
        super(AuthErrorCode.EXPIRED_TOKEN);
    }
}

package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class UnsupportedTokenException extends OutsourcingException {
    public UnsupportedTokenException() {
        super(AuthErrorCode.UNSUPPORTED_TOKEN);
    }
}

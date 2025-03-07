package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class NotFoundTokenException extends OutsourcingException {
    public NotFoundTokenException() {
        super(AuthErrorCode.NOT_FOUND_TOKEN);
    }
}

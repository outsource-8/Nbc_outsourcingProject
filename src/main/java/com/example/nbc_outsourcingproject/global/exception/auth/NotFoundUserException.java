package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class NotFoundUserException extends OutsourcingException {
    public NotFoundUserException() {
        super(AuthErrorCode.NOT_FOUND_USER);
    }
}

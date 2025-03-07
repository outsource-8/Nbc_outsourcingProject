package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class WrongPasswordException extends OutsourcingException {
    public WrongPasswordException() {
        super(AuthErrorCode.WRONG_PASSWORD);
    }

}

package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.AuthErrorCode;

public class InvalidRoleException extends OutsourcingException {
    public InvalidRoleException() {
        super(AuthErrorCode.INVALID_ROLE);
    }
}

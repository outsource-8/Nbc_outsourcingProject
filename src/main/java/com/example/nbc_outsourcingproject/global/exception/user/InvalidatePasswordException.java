package com.example.nbc_outsourcingproject.global.exception.user;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.UserErrorCode;

public class InvalidatePasswordException extends OutsourcingException {
    public InvalidatePasswordException() {
        super(UserErrorCode.INVALIDATE_PASSWORD);
    }
}

package com.example.nbc_outsourcingproject.global.exception.auth;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class InvalidateRefreshTokenException extends OutsourcingException {

    public InvalidateRefreshTokenException() {
        super(MenuErrorCode.DUPLICATE_MENU);
    }
}

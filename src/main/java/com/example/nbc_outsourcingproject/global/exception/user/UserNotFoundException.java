package com.example.nbc_outsourcingproject.global.exception.user;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.UserErrorCode;

public class UserNotFoundException extends OutsourcingException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}

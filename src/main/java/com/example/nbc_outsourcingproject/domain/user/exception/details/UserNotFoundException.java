package com.example.nbc_outsourcingproject.domain.user.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.user.exception.UserErrorCode;

public class UserNotFoundException extends OutsourcingException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class InvalidStoreMenuException extends OutsourcingException {
    public InvalidStoreMenuException() {
        super(MenuErrorCode.INVALID_STORE_MENU);
    }
}

package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class InvalidStoreMenuException extends OutsourcingException {
    public InvalidStoreMenuException() {
        super(MenuErrorCode.INVALID_STORE_MENU);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class InvalidStoreMenuException extends MenuException {
    public InvalidStoreMenuException() {
        super(MenuExceptionCode.INVALID_STORE_MENU);
    }
}

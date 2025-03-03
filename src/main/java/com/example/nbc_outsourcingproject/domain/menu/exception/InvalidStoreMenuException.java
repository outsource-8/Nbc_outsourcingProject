package com.example.nbc_outsourcingproject.domain.menu.exception;

public class InvalidStoreMenuException extends MenuException {
    public InvalidStoreMenuException() {
        super(MenuExceptionCode.INVALID_STORE_MENU);
    }
}

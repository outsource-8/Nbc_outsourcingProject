package com.example.nbc_outsourcingproject.domain.menu.exception;

public class StoreNotFoundException extends MenuException {
    public StoreNotFoundException() {
        super(MenuExceptionCode.STORE_NOT_FOUND);
    }
}

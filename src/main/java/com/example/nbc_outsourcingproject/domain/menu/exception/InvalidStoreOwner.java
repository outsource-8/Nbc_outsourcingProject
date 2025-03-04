package com.example.nbc_outsourcingproject.domain.menu.exception;

public class InvalidStoreOwner extends MenuException {
    public InvalidStoreOwner() {
        super(MenuExceptionCode.INVALID_STORE_OWNER);
    }
}

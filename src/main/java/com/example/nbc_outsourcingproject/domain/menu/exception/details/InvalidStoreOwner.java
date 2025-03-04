package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class InvalidStoreOwner extends MenuException {
    public InvalidStoreOwner() {
        super(MenuExceptionCode.INVALID_STORE_OWNER);
    }
}

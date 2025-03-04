package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class StoreNotFoundException extends MenuException {
    public StoreNotFoundException() {
        super(MenuExceptionCode.STORE_NOT_FOUND);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class StoreNotFoundException extends OutsourcingException {
    public StoreNotFoundException() {
        super(MenuErrorCode.STORE_NOT_FOUND);
    }
}

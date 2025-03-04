package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class InvalidStoreOwner extends OutsourcingException {
    public InvalidStoreOwner() {
        super(MenuErrorCode.INVALID_STORE_OWNER);
    }
}

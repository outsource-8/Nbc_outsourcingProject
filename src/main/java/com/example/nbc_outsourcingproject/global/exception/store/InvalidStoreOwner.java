package com.example.nbc_outsourcingproject.global.exception.store;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;
import com.example.nbc_outsourcingproject.global.exception.errorcode.StoreErrorCode;

public class InvalidStoreOwner extends OutsourcingException {
    public InvalidStoreOwner() {
        super(StoreErrorCode.INVALID_STORE_OWNER);
    }
}

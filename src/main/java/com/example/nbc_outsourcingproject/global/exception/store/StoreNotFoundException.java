package com.example.nbc_outsourcingproject.global.exception.store;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.StoreErrorCode;

public class StoreNotFoundException extends OutsourcingException {
    public StoreNotFoundException() {
        super(StoreErrorCode.STORE_NOT_FOUND);
    }
}

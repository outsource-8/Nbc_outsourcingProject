package com.example.nbc_outsourcingproject.global.exception.store;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.StoreErrorCode;

public class MaxStoreCreationException extends OutsourcingException {
    public MaxStoreCreationException() {
        super(StoreErrorCode.MAX_STORE_CREATION);
    }
}

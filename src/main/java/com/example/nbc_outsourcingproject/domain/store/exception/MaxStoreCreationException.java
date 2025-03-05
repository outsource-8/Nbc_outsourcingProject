package com.example.nbc_outsourcingproject.domain.store.exception;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;

public class MaxStoreCreationException extends OutsourcingException {
    public MaxStoreCreationException() {
        super(StoreErrorCode.MAX_STORE_CREATION);
    }
}

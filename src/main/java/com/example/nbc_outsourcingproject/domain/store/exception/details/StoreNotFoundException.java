package com.example.nbc_outsourcingproject.domain.store.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.store.exception.StoreErrorCode;

public class StoreNotFoundException extends OutsourcingException {
    public StoreNotFoundException() {
        super(StoreErrorCode.MAX_STORE_CREATION);
    }
}

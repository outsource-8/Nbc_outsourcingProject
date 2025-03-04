package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class DuplicateMenuException extends OutsourcingException {

    public DuplicateMenuException() {
        super(MenuErrorCode.DUPLICATE_MENU);
    }
}

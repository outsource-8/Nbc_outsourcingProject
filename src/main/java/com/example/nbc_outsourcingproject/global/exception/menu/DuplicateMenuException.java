package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class DuplicateMenuException extends OutsourcingException {

    public DuplicateMenuException() {
        super(MenuErrorCode.DUPLICATE_MENU);
    }
}

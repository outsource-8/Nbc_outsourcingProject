package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class DuplicateMenuException extends MenuException {

    public DuplicateMenuException() {
        super(MenuExceptionCode.DUPLICATE_MENU);
    }
}

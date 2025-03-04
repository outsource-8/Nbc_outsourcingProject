package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class MenuNotFoundException extends MenuException {
    public MenuNotFoundException() {
        super(MenuExceptionCode.MENU_NOT_FOUND);
    }
}

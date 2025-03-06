package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class MenuNotFoundException extends OutsourcingException {
    public MenuNotFoundException() {
        super(MenuErrorCode.MENU_NOT_FOUND);
    }
}

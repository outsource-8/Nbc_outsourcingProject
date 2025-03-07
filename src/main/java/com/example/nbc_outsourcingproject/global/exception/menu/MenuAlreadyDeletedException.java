package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class MenuAlreadyDeletedException extends OutsourcingException {
    public MenuAlreadyDeletedException() {
        super(MenuErrorCode.MENU_ALREADY_DELETED);
    }
}

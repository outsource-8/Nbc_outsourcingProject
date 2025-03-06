package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class MenuOptionNotFoundException extends OutsourcingException {
    public MenuOptionNotFoundException() {
        super(MenuErrorCode.OPTION_NOT_FOUND);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class MenuNotFoundException extends OutsourcingException {
    public MenuNotFoundException() {
        super(MenuErrorCode.MENU_NOT_FOUND);
    }
}

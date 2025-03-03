package com.example.nbc_outsourcingproject.domain.menu.exception;

public class MenuNotFoundException extends MenuException{
    public MenuNotFoundException() {
        super(MenuExceptionCode.MENU_NOT_FOUND);
    }
}

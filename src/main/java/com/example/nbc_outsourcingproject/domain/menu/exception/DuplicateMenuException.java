package com.example.nbc_outsourcingproject.domain.menu.exception;

public class DuplicateMenuException extends MenuException{

    public DuplicateMenuException() {
        super(MenuExceptionCode.DUPLICATE_MENU);
    }
}

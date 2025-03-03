package com.example.nbc_outsourcingproject.domain.menu.exception;

public class CategoryNotFoundException extends MenuException {
    public CategoryNotFoundException() {
        super(MenuExceptionCode.CATEGORY_NOT_FOUND);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuExceptionCode;

public class CategoryNotFoundException extends MenuException {
    public CategoryNotFoundException() {
        super(MenuExceptionCode.CATEGORY_NOT_FOUND);
    }
}

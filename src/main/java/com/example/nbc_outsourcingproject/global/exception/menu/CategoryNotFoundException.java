package com.example.nbc_outsourcingproject.global.exception.menu;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.MenuErrorCode;

public class CategoryNotFoundException extends OutsourcingException {
    public CategoryNotFoundException() {
        super(MenuErrorCode.CATEGORY_NOT_FOUND);
    }
}

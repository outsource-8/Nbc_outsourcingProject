package com.example.nbc_outsourcingproject.domain.menu.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuErrorCode;

public class CategoryNotFoundException extends OutsourcingException {
    public CategoryNotFoundException() {
        super(MenuErrorCode.CATEGORY_NOT_FOUND);
    }
}

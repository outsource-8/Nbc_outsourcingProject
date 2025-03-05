package com.example.nbc_outsourcingproject.domain.menuoption.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.menuoption.exception.MenuOptionErrorCode;

public class MenuOptionNotFoundException extends OutsourcingException {
    public MenuOptionNotFoundException() {
        super(MenuOptionErrorCode.OPTION_NOT_FOUND);
    }
}

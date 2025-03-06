package com.example.nbc_outsourcingproject.global.exception.order;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.OrderErrorCode;

public class MinOrderAmountException extends OutsourcingException {
    public MinOrderAmountException(){
        super(OrderErrorCode.ORDER_NOT_FOUND);
    }
}
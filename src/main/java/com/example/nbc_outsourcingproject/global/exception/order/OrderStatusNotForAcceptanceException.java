package com.example.nbc_outsourcingproject.global.exception.order;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.OrderErrorCode;

public class OrderStatusNotForAcceptanceException extends OutsourcingException {
    public OrderStatusNotForAcceptanceException(){
        super(OrderErrorCode.INVALID_ORDER_ACCEPTANCE_STATUS);
    }
}
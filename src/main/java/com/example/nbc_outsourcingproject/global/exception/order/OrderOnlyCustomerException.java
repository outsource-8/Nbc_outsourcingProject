package com.example.nbc_outsourcingproject.global.exception.order;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.OrderErrorCode;

public class OrderOnlyCustomerException extends OutsourcingException {
    public OrderOnlyCustomerException(){
        super(OrderErrorCode.ORDER_ONLY_CUSTOMER);
    }
}
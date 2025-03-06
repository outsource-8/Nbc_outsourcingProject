package com.example.nbc_outsourcingproject.domain.order.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.details.OutsourcingException;
import com.example.nbc_outsourcingproject.domain.order.exception.OrderErrorCode;

public class OrderNotFoundException extends OutsourcingException {
    public OrderNotFoundException() {
        super(OrderErrorCode.ORDER_NOT_FOUND);
    }
}

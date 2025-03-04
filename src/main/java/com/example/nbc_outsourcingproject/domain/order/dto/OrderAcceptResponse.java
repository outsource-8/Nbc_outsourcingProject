package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderAcceptResponse {

    private final Long orderId;
    private final OrderStatus status;

    public OrderAcceptResponse(Long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}

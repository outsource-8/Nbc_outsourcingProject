package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderAcceptedResponse {

    private final Long orderId;
    private final OrderStatus status;

    public OrderAcceptedResponse(Long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}

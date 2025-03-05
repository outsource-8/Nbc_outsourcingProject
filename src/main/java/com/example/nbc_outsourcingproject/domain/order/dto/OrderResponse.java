package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderResponse {

    private final Long orderId;

    public OrderResponse(Long orderId) {
        this.orderId = orderId;
    }
}

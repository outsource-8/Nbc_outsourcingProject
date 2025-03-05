package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class OrderSaveResponse {

    private final Long orderId;

    public OrderSaveResponse(Long orderId) {
        this.orderId = orderId;
    }
}

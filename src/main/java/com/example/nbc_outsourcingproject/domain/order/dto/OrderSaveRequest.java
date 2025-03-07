package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderSaveRequest {

    private Long menuId;
    private List<Long> optionIds;
    private int quantity;

    public OrderSaveRequest(Long menuId, List<Long> optionIds, int quantity) {
        this.menuId = menuId;
        this.optionIds = optionIds;
        this.quantity = quantity;
    }
}

package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderSaveRequest {

    private Long menuId;
    private int quantity;
    private Long optionId;
}

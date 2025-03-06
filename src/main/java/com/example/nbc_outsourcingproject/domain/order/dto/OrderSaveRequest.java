package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderSaveRequest {

    private Long menuId;
    private int quantity;
    private List<Long> optionIds;
}

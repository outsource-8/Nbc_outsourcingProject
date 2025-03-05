package com.example.nbc_outsourcingproject.domain.order.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class OrderSaveRequest {

//    private Map<Long, Integer> orders;

    private Long menuId;
    private int quantity;
}

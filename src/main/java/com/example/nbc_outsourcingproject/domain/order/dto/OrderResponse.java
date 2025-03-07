package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private final Long orderId;
    private final List<OrderMenuResponse> orderMenus;
    private final int totalAmount;
    private final OrderStatus status;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.orderMenus = Optional.ofNullable(order.getOrderMenus())
                .orElse(Collections.emptyList())
                .stream()
                .map(OrderMenuResponse::new)
                .collect(Collectors.toList());
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
    }

}

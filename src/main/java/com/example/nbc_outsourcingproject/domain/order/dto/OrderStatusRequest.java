package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusRequest {

   private Long orderId;
   private OrderStatus status;
}

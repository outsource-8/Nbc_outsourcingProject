package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusResponse {

   private final Long orderId;
   private final OrderStatus status;

   public OrderStatusResponse(Long orderId, OrderStatus status) {
      this.orderId = orderId;
      this.status = status;
   }
}

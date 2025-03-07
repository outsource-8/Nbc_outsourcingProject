package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderAcceptedRequest {

   private Long orderId;
   private OrderStatus status;

   public OrderAcceptedRequest(Long orderId, OrderStatus status) {
      this.orderId = orderId;
      this.status = status;
   }
}

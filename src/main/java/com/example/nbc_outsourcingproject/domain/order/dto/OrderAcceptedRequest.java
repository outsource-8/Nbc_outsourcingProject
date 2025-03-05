package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class OrderAcceptedRequest {

   private Long orderId;
   private OrderStatus status;

}

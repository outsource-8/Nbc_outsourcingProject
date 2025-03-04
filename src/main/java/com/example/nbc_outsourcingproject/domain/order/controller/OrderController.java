package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.config.aop.annotation.Order;
import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveRequest;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveResponse;
import com.example.nbc_outsourcingproject.domain.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Order
    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<OrderSaveResponse> createOrder (
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody List<OrderSaveRequest> menus
    ) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.createOrder(authUser, storeId, menus));
    }

    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders (
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<OrderResponse> result = orderService.getOrders(authUser, storeId, page, size);
        return ResponseEntity.ok(result);
    }

}

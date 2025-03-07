package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.global.aop.annotation.Order;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveRequest;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveResponse;
import com.example.nbc_outsourcingproject.domain.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User용 주문 API")
public class OrderController {

    private final OrderService orderService;

    @Order
    @PostMapping("/stores/{storeId}/orders")
    @Operation(summary = "주문 생성")
    public ResponseEntity<OrderSaveResponse> createOrder (
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody List<OrderSaveRequest> menus
    ) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.createOrder(authUser, storeId, menus));
    }

    @GetMapping("/stores/{storeId}/orders")
    @Operation(summary = "주문 다건 조회")
    public ResponseEntity<Page<OrderResponse>> getOrders (
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<OrderResponse> result = orderService.getOrders(authUser, storeId, page, size);
        return ResponseEntity.ok(result);
    }

}

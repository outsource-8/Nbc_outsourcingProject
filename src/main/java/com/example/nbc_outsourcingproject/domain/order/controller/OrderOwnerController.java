package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.global.aop.annotation.Order;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.*;
import com.example.nbc_outsourcingproject.domain.order.service.OrderOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderOwnerController {

    private final OrderOwnerService orderOwnerService;

    @GetMapping("/owner/stores/{storeId}/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders (
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<OrderResponse> result = orderOwnerService.getOrders(authUser, storeId, page, size);
        return ResponseEntity.ok(result);
    }


    @Order
    @PatchMapping("/owner/stores/{storeId}/orders/accepted")
    public ResponseEntity<OrderAcceptedResponse> updateOrderAccepted (
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody OrderAcceptedRequest dto
    ){
        return ResponseEntity.ok(orderOwnerService.updateOrderAccepted(authUser, storeId, dto));
    }

    @Order
    @PatchMapping("/owner/stores/{storeId}/orders/status")
    public ResponseEntity<OrderStatusResponse> updateOrderStatus (
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody OrderStatusRequest dto
    ){
        return ResponseEntity.ok(orderOwnerService.updateOrderStatus(authUser, storeId, dto));
    }

}

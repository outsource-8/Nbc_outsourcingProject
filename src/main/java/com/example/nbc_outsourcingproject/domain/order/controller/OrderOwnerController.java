package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.config.aop.annotation.Order;
import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.*;
import com.example.nbc_outsourcingproject.domain.order.service.OrderOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PatchMapping("/owner/stores/{storeId}/orders")
    public ResponseEntity<OrderAcceptResponse> updateOrderAccept (@RequestBody OrderAcceptRequest dto
                             , @Auth AuthUser authUser
    ){
        return ResponseEntity.ok(orderOwnerService.updateOrderAccepted(dto, authUser));
    }

}

package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //    @GetMapping("/stores/{storeId}/orders")
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders (
//            @Auth AuthUser authUser
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<OrderResponse> result = orderService.getOrders(page, size);
        return ResponseEntity.ok(result);
    }

}

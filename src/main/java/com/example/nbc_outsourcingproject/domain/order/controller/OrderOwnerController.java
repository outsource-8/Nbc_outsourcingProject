package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.config.aop.annotation.Order;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveRequest;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveResponse;
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

    @Order
//    @PostMapping("/stores/{storeId}/orders")
    @PostMapping("/orders")
    public ResponseEntity<OrderSaveResponse> createOrder (@RequestBody List<OrderSaveRequest> orders
//                             , @Auth AuthUser authUser
    ){
       return ResponseEntity.ok(orderOwnerService.createOrder(orders));
    }

    //    @GetMapping("/stores/{storeId}/orders")
    @GetMapping("/orders/owner")
    public ResponseEntity<Page<OrderResponse>> getOrders (
//            @Auth AuthUser authUser
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        Page<OrderResponse> result = orderOwnerService.getOrders(page, size);
        return ResponseEntity.ok(result);
    }


//    @Order
////    @PostMapping("/stores/{storeId}/orders")
//    @PatchMapping("/orders/owner/{orderId}")
//    public OrderSaveResponse updateOrderAccept (@RequestBody OrderAcceptRequest dto
////                             , @Auth AuthUser authUser
//                                                ,Long orderId
//    ){
//        return orderOwnerService.updateOrderAccept(dto, orderId);
//    }

}

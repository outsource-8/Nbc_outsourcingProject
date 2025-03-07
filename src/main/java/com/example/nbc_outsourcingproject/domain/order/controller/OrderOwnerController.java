package com.example.nbc_outsourcingproject.domain.order.controller;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.order.dto.*;
import com.example.nbc_outsourcingproject.domain.order.service.OrderOwnerService;
import com.example.nbc_outsourcingproject.global.aop.annotation.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Owner용 주문 관리 API", description = "Owner가 사용하는 주문관리 API 입니다.")
public class OrderOwnerController {

    private final OrderOwnerService orderOwnerService;

    @GetMapping("/owner/stores/{storeId}/orders")
    @Operation(summary = "주문 조회")
    public ResponseEntity<Page<OrderResponse>> getOrders (
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<OrderResponse> result = orderOwnerService.getOrders(authUser, storeId, page, size);
        return ResponseEntity.ok(result);
    }


    @Order
    @PatchMapping("/owner/stores/{storeId}/orders/accepted")
    @Operation(summary = "주문 수락여부 설정")
    public ResponseEntity<OrderAcceptedResponse> updateOrderAccepted (
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody OrderAcceptedRequest dto
    ){
        return ResponseEntity.ok(orderOwnerService.updateOrderAccepted(authUser, storeId, dto));
    }

    @Order
    @PatchMapping("/owner/stores/{storeId}/orders/status")
    @Operation(summary = "주문 상태 변경")
    public ResponseEntity<OrderStatusResponse> updateOrderStatus (
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @RequestBody OrderStatusRequest dto
    ){
        return ResponseEntity.ok(orderOwnerService.updateOrderStatus(authUser, storeId, dto));
    }

}

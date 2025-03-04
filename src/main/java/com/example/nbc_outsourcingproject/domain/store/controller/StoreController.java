package com.example.nbc_outsourcingproject.domain.store.controller;

import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User용 가게 API")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores")
    @Operation(summary = "가게 다건 조회")
    public ResponseEntity<Page<StoreResponse>> getStores(
            @RequestParam(required = false) String StoreName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(storeService.getStores(StoreName, page, size));
    }

}

package com.example.nbc_outsourcingproject.domain.store.controller;

import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;

    // 가게 생성
    @PostMapping("/stores")
    public ResponseEntity<StoreSaveResponse> createStore(
            User user,
            @Valid @RequestBody StoreSaveRequest storeSaveRequest
    ) {
        return ResponseEntity.ok(storeOwnerService.saveStore(user, storeSaveRequest));
    }

    // 소유한 가게 조회
    @GetMapping("/stores/mine")
    public ResponseEntity<List<StoreResponse>> getStoresMine(
            @RequestParam() Long userId
    ) {
        return ResponseEntity.ok(storeOwnerService.getStoresMine(userId));
    }
}
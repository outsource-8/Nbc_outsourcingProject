package com.example.nbc_outsourcingproject.store.store.controller;

import com.example.nbc_outsourcingproject.store.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.service.StoreOwnerService;
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
            FakeUser fakeUser,
            @Valid @RequestBody StoreSaveRequest storeSaveRequest
    ) {
        return ResponseEntity.ok(storeOwnerService.saveStore(fakeUser, storeSaveRequest));
    }

    // 소유한 가게 조회
    @GetMapping("/stores/mine")
    public ResponseEntity<List<StoreResponse>> getStoresMine(
            @RequestParam() Long fakeUserId
    ){
        return ResponseEntity.ok(storeOwnerService.getStoresMine(fakeUserId));
    }
}
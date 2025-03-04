package com.example.nbc_outsourcingproject.domain.store.controller;

import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreUpdateRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Owner용 가게 관리 API", description = "Owner가 사용하는 가게관리 API 입니다.")
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;

    // 가게 생성
    @PostMapping("/stores")
    @Operation(summary = "가게 생성")
    public ResponseEntity<StoreSaveResponse> createStore(
            @Auth AuthUser user,
            @Valid @RequestBody StoreSaveRequest storeSaveRequest
    ) {
        return ResponseEntity.ok(storeOwnerService.saveStore(user, storeSaveRequest));
    }

    // 소유한 가게 조회
    @GetMapping("/stores/mine")
    @Operation(summary = "소유한 가게 조회")
    public ResponseEntity<List<StoreResponse>> getStoresMine(
            @Auth AuthUser user
    ) {
        return ResponseEntity.ok(storeOwnerService.getStoresMine(user));
    }

    // 가게 정보 수정
    @PatchMapping("/stores/mine/{storeId}")
    @Operation(summary = "가게 정보 수정")
    public ResponseEntity<StoreResponse> updateStore(
            @Auth AuthUser user,
            @PathVariable Long storeId,
            @Valid @RequestBody StoreUpdateRequest storeUpdateRequest
    ) {
        return ResponseEntity.ok(storeOwnerService.updateStore(user, storeId, storeUpdateRequest));
    }

    // 가게 폐업 처리
}
package com.example.nbc_outsourcingproject.store.store.controller;

import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.service.StoreOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;

    @PostMapping("/stores")
    public ResponseEntity<StoreSaveResponse> createStore(
            FakeUser fakeUser,
            @Valid @RequestBody StoreSaveRequest storeSaveRequest
    ) {
        return ResponseEntity.ok(storeOwnerService.saveStore(fakeUser, storeSaveRequest));
    }
}
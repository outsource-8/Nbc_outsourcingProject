package com.example.nbc_outsourcingproject.domain.store.service;

import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    // 가게 다건 조회
    public Page<StoreResponse> getStores(String storeName, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Store> stores = storeRepository.findStores(storeName, pageable);
        return stores.map(store -> new StoreResponse(
                store.getName(),
                store.getStoreInfo(),
                store.getAddress(),
                store.getMinOrderAmount(),
                store.getClosed(),
                store.getOpened()
        ));
    }
}

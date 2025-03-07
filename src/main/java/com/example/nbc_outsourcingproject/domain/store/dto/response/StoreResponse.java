package com.example.nbc_outsourcingproject.domain.store.dto.response;

import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Builder
@Getter
public class StoreResponse {
    private final String name;
    private final String storeInfo;
    private final String address;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;

    public static StoreResponse from (Store store) {
        return StoreResponse.builder()
                .name(store.getName())
                .storeInfo(store.getStoreInfo())
                .address(store.getAddress())
                .minOrderAmount(store.getMinOrderAmount())
                .opened(store.getOpened())
                .closed(store.getClosed())
                .build();
    }
}

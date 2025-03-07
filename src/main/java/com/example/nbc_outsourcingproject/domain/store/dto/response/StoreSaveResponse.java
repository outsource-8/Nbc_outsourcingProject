package com.example.nbc_outsourcingproject.domain.store.dto.response;

import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class StoreSaveResponse {
    private final Long id;
    private final String name;
    private final String storeInfo;
    private final String address;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;

    public static StoreSaveResponse from (Store store) {
        return StoreSaveResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .storeInfo(store.getStoreInfo())
                .address(store.getAddress())
                .minOrderAmount(store.getMinOrderAmount())
                .opened(store.getOpened())
                .closed(store.getClosed())
                .build();
    }

}

package com.example.nbc_outsourcingproject.domain.store.dto.response;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
public class StoreDetailResponse {
    private final String name;
    private final String storeInfo;
    private final String address;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;
    private final List<MenuResponse> menuResponseList;

    public static StoreDetailResponse from (Store store, List<MenuResponse> menu){
        return StoreDetailResponse.builder()
                .name(store.getName())
                .storeInfo(store.getStoreInfo())
                .address(store.getAddress())
                .minOrderAmount(store.getMinOrderAmount())
                .opened(store.getOpened())
                .closed(store.getClosed())
                .menuResponseList(menu)
                .build();
    }
}

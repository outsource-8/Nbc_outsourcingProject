package com.example.nbc_outsourcingproject.store.store.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreResponse {
    private final String name;
    private final String storeInfo;
    private final int minOrderAmount;
    private final LocalTime closed;
    private final LocalTime opened;

    public StoreResponse(String name, String storeInfo, int minOrderAmount, LocalTime closed, LocalTime opened) {
        this.name = name;
        this.storeInfo = storeInfo;
        this.minOrderAmount = minOrderAmount;
        this.closed = closed;
        this.opened = opened;
    }
}

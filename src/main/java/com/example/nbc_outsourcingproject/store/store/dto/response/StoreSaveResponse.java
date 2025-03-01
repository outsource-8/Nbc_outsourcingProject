package com.example.nbc_outsourcingproject.store.store.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreSaveResponse {
    private final String name;
    private final String storeInfo;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;

    public StoreSaveResponse(String name, String storeInfo, int minOrderAmount, LocalTime opened, LocalTime closed) {
        this.name = name;
        this.storeInfo = storeInfo;
        this.minOrderAmount = minOrderAmount;
        this.opened = opened;
        this.closed = closed;
    }

}

package com.example.nbc_outsourcingproject.domain.store.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreResponse {
    private final String name;
    private final String storeInfo;
    private final String address;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;


    public StoreResponse(String name, String storeInfo, String address, int minOrderAmount, LocalTime opened, LocalTime closed) {
        this.name = name;
        this.storeInfo = storeInfo;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.opened = opened;
        this.closed = closed;
    }
}

package com.example.nbc_outsourcingproject.domain.store.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreSaveResponse {
    private final Long id;
    private final String name;
    private final String storeInfo;
    private final int minOrderAmount;
    private final LocalTime opened;
    private final LocalTime closed;

    public StoreSaveResponse(Long id,String name, String storeInfo, int minOrderAmount, LocalTime opened, LocalTime closed) {
        this.id = id;
        this.name = name;
        this.storeInfo = storeInfo;
        this.minOrderAmount = minOrderAmount;
        this.opened = opened;
        this.closed = closed;
    }

}

package com.example.nbc_outsourcingproject.store.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StoreSaveResponse {
    private String name;
    private String storeInfo;
    private int minOrderAmount;
    private LocalTime closed;
    private LocalTime opened;

}

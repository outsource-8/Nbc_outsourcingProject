package com.example.nbc_outsourcingproject.domain.common;

import java.time.LocalTime;

public class ConfirmStoreOpen {

    public static boolean isOpened(LocalTime openTime, LocalTime closeTime) {
        LocalTime now = LocalTime.now();

        // 당일 영업
        // closeTime이 지금 시간보다 뒤
        if (closeTime.isAfter(openTime)) {
            // 지금 시간이 open보다 뒤 and 지금 시간이 close보다 전 = true
            return now.isAfter(openTime) && now.isBefore(closeTime);
        }

        // 익일 영업
        // 지금 시간이 open보다 뒤 or 지금 시간이 close보다 전 = true
        return now.isAfter(openTime) || now.isBefore(closeTime);

        // 지금 시간이 open보다 전 and 지금 시간이 close보다 뒤 = false
    }
}

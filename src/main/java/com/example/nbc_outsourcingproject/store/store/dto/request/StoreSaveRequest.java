package com.example.nbc_outsourcingproject.store.store.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreSaveRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 20, message = "이름의 최대 길이는 20자 입니다.")
    private String name;

    @NotBlank (message = "주소를 입력해 주세요.")
    private String address;

    @NotBlank(message = "주문 최소 금액을 입력해 주세요.")
    @Min(value = 0, message = "최소 주문 금액은 0보다 커야 합니다.")
    private int minOrderAmount;

    private String storeInfo;

    @NotBlank(message = "오픈 시간을 입력해주세요.")
    private LocalTime opened;

    @NotBlank(message = "마감 시간을 입력해주세요.")
    private LocalTime closed;

}


package com.example.nbc_outsourcingproject.domain.menu.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class MenuUpdateRequest {

    private String category;

    private String name;

    @Min(value = 0, message = "음수 값을 넣을 수 없습니다.")
    private int price;

    private String info;

}
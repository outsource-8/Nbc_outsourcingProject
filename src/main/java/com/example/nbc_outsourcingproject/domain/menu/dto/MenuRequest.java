package com.example.nbc_outsourcingproject.domain.menu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequest {

    @NotBlank(message = "카테고리를 설정해주세요.")
    private String category;

    @NotBlank(message = "메뉴명을 입력해주세요.")
    private String name;

    @NotNull(message = "메뉴 가격을 설정해주세요.")
    @Min(value = 0, message = "음수 값을 넣을 수 없습니다.")
    private Integer price;

    private String info;

}
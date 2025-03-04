package com.example.nbc_outsourcingproject.domain.menuoption.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuOptionRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    private String text;
    private Integer price;
}

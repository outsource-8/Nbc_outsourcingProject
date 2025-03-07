package com.example.nbc_outsourcingproject.domain.menu.dto;

import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Getter
@Builder
public class OwnerMenuResponse {

    private String category;
    private String name;
    private Integer price;
    private String info;
    private Boolean isDeleted;


    public static OwnerMenuResponse from(Menu menu) {
        return OwnerMenuResponse.builder()
                .category(menu.getCategory().name())
                .name(menu.getName())
                .price(menu.getPrice())
                .info(menu.getInfo())
                .isDeleted(menu.getIsDeleted())
                .build();
    }

    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    public String getPrice(){
        return NumberFormat.getNumberInstance(Locale.KOREA).format(price);
    }
}

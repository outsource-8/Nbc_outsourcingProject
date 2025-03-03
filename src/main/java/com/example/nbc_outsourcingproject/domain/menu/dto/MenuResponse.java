package com.example.nbc_outsourcingproject.domain.menu.dto;

import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Builder
public class MenuResponse {

    private String category;
    private String name;
    private int price;
    private String info;


    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .category(menu.getCategory().name())
                .name(menu.getName())
                .price(menu.getPrice())
                .info(menu.getInfo())
                .build();
    }

    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    public String getPrice(){
        return NumberFormat.getNumberInstance(Locale.KOREA).format(price);
    }
}
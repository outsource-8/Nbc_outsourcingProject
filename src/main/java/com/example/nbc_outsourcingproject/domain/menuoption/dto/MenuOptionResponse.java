package com.example.nbc_outsourcingproject.domain.menuoption.dto;

import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuOptionResponse {
    private String menu;
    private String text;
    private Integer price;

    public static MenuOptionResponse of(String menu, String text, Integer price) {
        return MenuOptionResponse.builder()
                .menu(menu)
                .text(text)
                .price(price)
                .build();
    }

    public static MenuOptionResponse from(MenuOption menuOption) {
        return MenuOptionResponse.builder()
                .menu(menuOption.getMenu().getName())
                .text(menuOption.getText())
                .price(menuOption.getPrice())
                .build();
    }
}

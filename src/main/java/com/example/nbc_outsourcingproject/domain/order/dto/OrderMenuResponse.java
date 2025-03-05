package com.example.nbc_outsourcingproject.domain.order.dto;

import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.example.nbc_outsourcingproject.domain.order.entity.OrderMenu;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderMenuResponse {
    private final Long id;
    private final String menuName;
    private final int menuPrice;
    private final int quantity;
    private final String menuOptions;

    public OrderMenuResponse(OrderMenu orderMenu) {
        this.id = orderMenu.getId();
        this.menuName = orderMenu.getCurrentMenuName();
        this.menuPrice = orderMenu.getCurrentMenuPrice();  // 메뉴 이름 포함
        this.quantity = orderMenu.getQuantity();
        this.menuOptions = orderMenu.getMenuOptions();
    }
}


package com.example.nbc_outsourcingproject.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class OrderMenu {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String currentMenuName;

    private int currentMenuPrice;

    private int quantity;

    private String menuOptions;

    public OrderMenu(Order order, String currentMenuName, int currentMenuPrice, int quantity, String menuOptions) {
        this.order = order;
        this.currentMenuName = currentMenuName;
        this.currentMenuPrice = currentMenuPrice;
        this.quantity = quantity;
        this.menuOptions = menuOptions;
    }
}

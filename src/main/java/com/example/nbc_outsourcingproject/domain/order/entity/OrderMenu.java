package com.example.nbc_outsourcingproject.domain.order.entity;

import com.example.nbc_outsourcingproject.domain.delete.Menu;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
    private String menuName;
    private int menuPrice;
    private int quantity;

    public OrderMenu(Order order, String menuName, int menuPrice, int quantity) {
        this.order = order;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
    }

}

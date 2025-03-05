package com.example.nbc_outsourcingproject.domain.order.entity;

import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private String currentMenuName;
    private int currentMenuPrice;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_option_id")
//    private MenuOption menuOption;

    private int quantity;

    @OneToMany(mappedBy = "orderMenu", fetch = FetchType.LAZY)
    private List<MenuOption> menuOptions;

//    private String currentOptionName;
//    private int currentOptionPrice;

    public OrderMenu(Order order, String currentMenuName, int currentMenuPrice, int quantity, List<MenuOption> menuOptions) {
        this.order = order;
        this.currentMenuName = currentMenuName;
        this.currentMenuPrice = currentMenuPrice;
        this.quantity = quantity;
        this.menuOptions = menuOptions;
    }
}

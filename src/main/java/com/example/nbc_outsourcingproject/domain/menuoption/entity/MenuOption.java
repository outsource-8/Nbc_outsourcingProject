package com.example.nbc_outsourcingproject.domain.menuoption.entity;

import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "menu_option")
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private String text;

    @Min(0)
    private Integer price;


    public MenuOption() {
    }

    public MenuOption(Menu menu, String text, Integer price) {
        this.menu = menu;
        this.text = text;
        this.price = price;
    }

    public MenuOption updateOption(String text, Integer price) {
        this.text = text;
        this.price = price;
        return this;
    }
}

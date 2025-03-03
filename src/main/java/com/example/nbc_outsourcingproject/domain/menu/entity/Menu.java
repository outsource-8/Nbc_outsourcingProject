package com.example.nbc_outsourcingproject.domain.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_store_name",
                columnNames = {"store_id", "name"})
})
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Size(min = 0)
    private int price;

    private String info;

    private boolean isDeleted;


    public Menu() {
    }

    public Menu(Category category, String name, int price, String info) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.info = info;
    }
}

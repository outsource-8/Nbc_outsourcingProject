package com.example.nbc_outsourcingproject.domain.menu.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
@Table( uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_store_name",
                columnNames = {"store_id", "name"})
})
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int price;

    private String info;

    private boolean isDeleted;


    public Menu(){}


}

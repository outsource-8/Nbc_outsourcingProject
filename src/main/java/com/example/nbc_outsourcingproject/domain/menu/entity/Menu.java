package com.example.nbc_outsourcingproject.domain.menu.entity;

import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
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
    private Integer price;

    private String info;

    @ColumnDefault("false")
    private Boolean isDeleted;


    public Menu() {
    }

    public Menu(Store store, Category category, String name, int price, String info) {
        this.store = store;
        this.category = category;
        this.name = name;
        this.price = price;
        this.info = info;
    }

    public Menu update(Category category, String name, int price, String info) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.info = info;
        return this;
    }

    public void deleteMenu() {
        this.isDeleted = true;
    }
}

package com.example.nbc_outsourcingproject.domain.order.entity;

import com.example.nbc_outsourcingproject.domain.order.Timestamped;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public Order(User user, Store store) {
        this.user = user;
        this.store = store;
    }

    public void update(int totalAmount, OrderStatus status) {
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public void updateAccepted(OrderStatus status) {
        this.status = status;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}

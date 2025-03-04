package com.example.nbc_outsourcingproject.domain.order.entity;

import com.example.nbc_outsourcingproject.domain.delete.Store;
import com.example.nbc_outsourcingproject.domain.order.Timestamped;
import com.example.nbc_outsourcingproject.domain.delete.User;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public void update(int totalAmount, OrderStatus status) {
        this.totalAmount = totalAmount;
        this.status = status;
    }
}

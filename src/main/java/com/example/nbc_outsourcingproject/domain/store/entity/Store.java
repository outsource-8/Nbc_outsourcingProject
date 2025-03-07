package com.example.nbc_outsourcingproject.domain.store.entity;

import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreUpdateRequest;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true)
    private String name;

    private String address;

    @Column(name = "min_order_amount")
    private int minOrderAmount;

    @Column(name = "store_info")
    private String storeInfo;
    private LocalTime opened;
    private LocalTime closed;

    @Column(name = "is_shut_down")
    private Boolean isShutDown = false;

    @Builder
    public Store(
            User user,
            String name,
            String address,
            int minOrderAmount,
            String storeInfo,
            LocalTime opened,
            LocalTime closed) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.storeInfo = storeInfo;
        this.opened = opened;
        this.closed = closed;
    }

    public void updateFrom(StoreUpdateRequest request) {
        this.name = request.getName();
        this.address = request.getAddress();
        this.minOrderAmount = request.getMinOrderAmount();
        this.storeInfo = request.getStoreInfo();
        this.opened = request.getOpened();
        this.closed = request.getClosed();
    }

    public void shutDown(){
        this.isShutDown = true;
    }

}


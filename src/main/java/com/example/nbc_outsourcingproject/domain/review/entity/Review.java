package com.example.nbc_outsourcingproject.domain.review.entity;

import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Review(Integer rating, String content, Order order, User user, Store store) {
        this.rating = rating;
        this.content = content;
        this.order = order;
        this.user = user;
        this.store = store;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void updateRating(Integer rating) {
        this.rating = rating;
    }

    public void delete() {
        this.deleted = true;
    }
}

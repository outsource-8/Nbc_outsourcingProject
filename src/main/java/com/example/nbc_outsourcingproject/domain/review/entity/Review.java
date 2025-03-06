package com.example.nbc_outsourcingproject.domain.review.entity;

import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Review {

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

    public void updateReview(Integer newRating, String newContent) {
        this.rating = newRating;
        this.content = newContent;
    }

    public void delete() {
        this.deleted = true;
    }
}

package com.example.nbc_outsourcingproject.domain.order.repository;

import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByUserAndStoreAndStatusNot(User user, Store store, OrderStatus status);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.orderMenus WHERE o.store.id = :storeId AND o.user.id = :userId",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.store.id = :storeId AND o.user.id = :userId")
    Page<Order> findOrdersWithMenus(@Param("storeId") Long storeId, @Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.orderMenus WHERE o.store.id = :storeId",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.store.id = :storeId")
    Page<Order> findOrdersWithMenus2(@Param("storeId") Long storeId, Pageable pageable);
}

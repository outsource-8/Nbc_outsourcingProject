package com.example.nbc_outsourcingproject.domain.order.repository;

import com.example.nbc_outsourcingproject.domain.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
}

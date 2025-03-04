package com.example.nbc_outsourcingproject.domain.order.service;

import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.order.dto.*;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.entity.OrderMenu;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderMenuRepository;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderOwnerService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public Page<OrderResponse> getOrders(AuthUser authUser, Long storeId, int page, int size) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("User가 없습니다.")
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalStateException("store가 없습니다.")
        );

        if (store.getUser().getId() != user.getId()){
            throw new IllegalStateException("본인 가게가 아닙니다.");
        }

        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;

        // pageable 객체 생성, 수정일 기준 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        // Page 조회
        Page<Order> orderPage = orderRepository.findOrdersWithMenus2(storeId,pageable);
        return orderPage.map(OrderResponse::new);
    }

    @Transactional
    public OrderAcceptResponse updateOrderAccepted(OrderAcceptRequest dto, AuthUser authUser) {
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                () -> new IllegalStateException("주문 없음")
        );

        if (!(dto.getStatus().equals(OrderStatus.ACCEPTED) || dto.getStatus().equals(OrderStatus.CANCELED))){
            throw new IllegalStateException("주문 수락 혹은 취소 하세요.");
        }
        order.updateAccepted(dto.getStatus());
        return new OrderAcceptResponse(
                order.getId(),
                order.getStatus()
        );
    }
}

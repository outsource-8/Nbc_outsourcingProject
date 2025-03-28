package com.example.nbc_outsourcingproject.domain.order.service;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.*;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.order.OrderNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.order.OrderStatusNotForAcceptanceException;
import com.example.nbc_outsourcingproject.global.exception.store.StoreNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderOwnerService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MyStoreCache myStoreCache;

    public Page<OrderResponse> getOrders(AuthUser authUser, Long storeId, int page, int size) {
        User user = validateUser(authUser);
        Store store = validateStore(storeId);
        myStoreCache.validateStoreOwner(user.getId(),store.getId());

        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;

        // pageable 객체 생성, 수정일 기준 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        // Page 조회
        Page<Order> orderPage = orderRepository.findOrdersWithMenus2(storeId,pageable);
        return orderPage.map(OrderResponse::new);
    }

    @Transactional
    public OrderAcceptedResponse updateOrderAccepted(AuthUser authUser, Long storeId, OrderAcceptedRequest dto) {
        User user = validateUser(authUser);
        Store store = validateStore(storeId);
        myStoreCache.validateStoreOwner(user.getId(),store.getId());

        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                OrderNotFoundException::new
        );

        if (!(dto.getStatus().equals(OrderStatus.ACCEPTED) || dto.getStatus().equals(OrderStatus.CANCELED))){
            throw new OrderStatusNotForAcceptanceException();
        }
        order.updateAccepted(dto.getStatus());
        return new OrderAcceptedResponse(
                order.getId(),
                order.getStatus()
        );
    }

    @Transactional
    public OrderStatusResponse updateOrderStatus(AuthUser authUser, Long storeId, OrderStatusRequest dto) {
        User user = validateUser(authUser);
        Store store = validateStore(storeId);
        myStoreCache.validateStoreOwner(user.getId(),store.getId());

        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                () -> new OrderNotFoundException()
        );

        OrderStatus status = order.getStatus();
        switch (status){
            case PENDING ->  throw new IllegalStateException("/accepted에서 주문 수락, 취소 하세요.");
            case ACCEPTED -> {
                if (!(dto.getStatus().equals(OrderStatus.COOKING)||dto.getStatus().equals(OrderStatus.CANCELED))){
                    throw new IllegalStateException("현재 주문 수락 상태입니다. 다음 단계를 입력하세요.");
                }
                order.updateStatus(dto.getStatus());
            }
            case CANCELED -> {
                throw new IllegalStateException("취소된 주문은 수정할 수 없습니다.");
            }
            case COOKING -> {
                if (!(dto.getStatus().equals(OrderStatus.DELIVERING)||dto.getStatus().equals(OrderStatus.CANCELED))){
                    throw new IllegalStateException("현재 주문 조리중입니다. 다음 단계를 입력하세요.");
                }
                order.updateStatus(dto.getStatus());
            }
            case DELIVERING -> {
                if (!(dto.getStatus().equals(OrderStatus.COMPLETED)||dto.getStatus().equals(OrderStatus.CANCELED))){
                    throw new IllegalStateException("현재 배달중입니다. 다음 단계를 입력하세요.");
                }
                order.updateStatus(dto.getStatus());
            }
            case COMPLETED -> {
                throw new IllegalStateException("완료된 주문은 수정할 수 없습니다.");
            }
        }

        return new OrderStatusResponse(
                order.getId(),
                order.getStatus()
        );
    }

    private User validateUser(AuthUser authUser) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                UserNotFoundException::new
        );
        return user;
    }

    private Store validateStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                StoreNotFoundException::new
        );
        return store;
    }
}

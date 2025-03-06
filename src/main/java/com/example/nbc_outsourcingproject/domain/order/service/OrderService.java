package com.example.nbc_outsourcingproject.domain.order.service;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.common.ConfirmStoreOpen;
import com.example.nbc_outsourcingproject.global.exception.menu.MenuNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.store.StoreNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionRequest;
import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import com.example.nbc_outsourcingproject.domain.menuoption.repository.MenuOptionRepository;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveRequest;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderSaveResponse;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.entity.OrderMenu;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderMenuRepository;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderSaveResponse createOrder(AuthUser authUser, Long storeId, List<OrderSaveRequest> menus) throws JsonProcessingException {
        User user = validateUser(authUser);
        Store store = validateStore(storeId);
        validateRole(user);

        log.info(String.valueOf(ConfirmStoreOpen.isOpened(store.getOpened(), store.getClosed())));

        if (! ConfirmStoreOpen.isOpened(store.getOpened(), store.getClosed())) {
            throw new StoreNotFoundException();
        }

        if (orderRepository.existsByUserAndStoreAndStatusNot(user, store, OrderStatus.COMPLETED)) {
            throw new IllegalStateException("이미 해당 가게에 주문한 기록이 있습니다.");
        }

        if (menus == null || menus.isEmpty()) {
            throw new IllegalStateException("주문 내역이 없습니다.");
        }

        int totalAmount = 0;

        List<OrderMenu> orderMenus = new ArrayList<>();
        Order order = new Order(user, store);
        orderRepository.save(order);

        for (OrderSaveRequest m : menus) {
            Long menuId = m.getMenuId();
            int quantity = m.getQuantity();
            List<Long> optionIds = m.getOptionIds();

            Menu menu = menuRepository.findById(menuId).orElseThrow(
                    MenuNotFoundException::new
            );

            if (!menuOptionRepository.existsAllByIdAndMenu_Id(optionIds, optionIds.size(), menu.getId())) {
                throw new IllegalStateException("메뉴에 해당하는 옵션이 아닙니다.");
            }

            List<MenuOption> menuOptionList = menuOptionRepository.findByIdIn(optionIds);
            List<MenuOptionRequest> menuOptionRequests
                    = menuOptionList.stream()
                    .map(option -> new MenuOptionRequest(option.getText(), option.getPrice()))
                    .toList();
            String strOptionIds = objectMapper.writeValueAsString(menuOptionRequests).replace("\"", "");
            OrderMenu orderMenu = new OrderMenu(order, menu.getName(), menu.getPrice(), quantity, strOptionIds);
            int totalOptionAmount = menuOptionList.stream().mapToInt(MenuOption::getPrice).sum();
            totalAmount += (menu.getPrice() + totalOptionAmount) * quantity;

            orderMenus.add(orderMenu);
        }

        if (totalAmount < store.getMinOrderAmount()) {
            throw new IllegalStateException("최소주문금액보다 작습니다.");
        }

        //FIXME: 익일 영업 시간 계산 삭제 필요
//        LocalTime now = LocalTime.now();
//        if (now.isBefore(store.getOpened()) || now.isAfter(store.getClosed())) {
//            throw new IllegalStateException("영업시간이 아닙니다.");
//        }
        orderMenuRepository.saveAll(orderMenus);
        order.update(totalAmount, OrderStatus.PENDING);
        return new OrderSaveResponse(order.getId());
    }

    public Page<OrderResponse> getOrders(AuthUser authUser, Long storeId, int page, int size) {
        User user = validateUser(authUser);
        validateStore(storeId);
        validateRole(user);

        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;

        // pageable 객체 생성일 기준 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        // Page 조회
        Page<Order> orderPage = orderRepository.findOrdersWithMenus(storeId, authUser.getId(), pageable);
        return orderPage.map(OrderResponse::new);
    }

    private User validateUser(AuthUser authUser) {
        return userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("User가 없습니다.")
        );
    }

    private Store validateStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
                StoreNotFoundException::new
        );
    }

    private static void validateRole(User user) {
        if (user.getUserRole().equals(UserRole.OWNER)) {
            throw new IllegalStateException("고객만 주문 가능합니다.");
        }
    }
}

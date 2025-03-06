package com.example.nbc_outsourcingproject.domain.order.service;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import com.example.nbc_outsourcingproject.domain.menuoption.repository.MenuOptionRepository;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderAcceptedRequest;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderAcceptedResponse;
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
import com.example.nbc_outsourcingproject.global.exception.order.MinOrderAmountException;
import com.example.nbc_outsourcingproject.global.exception.order.OrderStatusNotForAcceptanceException;
import com.example.nbc_outsourcingproject.global.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderOwnerService orderOwnerService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuOptionRepository menuOptionRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMenuRepository orderMenuRepository;

    private User user;
    private AuthUser authUser;
    private Store store;
    private Menu menu;
    private MenuOption menuOption;
    private Order order;
    private OrderMenu orderMenu;

    @BeforeEach
    void setUp() {
        User user = new User("user@naver.com", "USER1234", "user", "seoul", UserRole.USER);
        userRepository.save(user);
        AuthUser authUser = new AuthUser(user.getId(),user.getEmail(),user.getUserRole());
        Store store = new Store(user,"가게1", "주소1", 14000, "가게 소개 1", LocalTime.of(9, 0), LocalTime.of(23, 30));
        storeRepository.save(store);
        Menu menu = new Menu(store,Category.MAIN,"김치찌개",12000,"김치찌개 맛있어요.");
        menuRepository.save(menu);
        MenuOption menuOption = new MenuOption(menu,"스팸추가",3000);
        menuOptionRepository.save(menuOption);
        Order order = new Order(user,store);
        orderRepository.save(order);
        OrderMenu orderMenu = new OrderMenu(order, "김치찌개", 12000, 10, menuOption.toString());
        orderMenuRepository.save(orderMenu);
        order.update((orderMenu.getCurrentMenuPrice()+ menuOption.getPrice())* orderMenu.getQuantity(), OrderStatus.PENDING);

        this.authUser = authUser;
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.menuOption = menuOption;
        this.order = order;
        this.orderMenu = orderMenu;
    }




    @Test
    public void 고객_주문_정상_생성() throws JsonProcessingException {
        //given
        OrderSaveRequest request = new OrderSaveRequest(1L, List.of(1L),orderMenu.getQuantity());
        List<OrderSaveRequest> requests = new ArrayList<>();
        requests.add(request);

        //when
        OrderSaveResponse response = orderService.createOrder(authUser, store.getId(), requests);

        //then
        Order createdOrder = orderRepository.findById(response.getOrderId())
                .orElseThrow(() -> new AssertionError("주문이 데이터베이스에 저장되지 않았습니다"));
        assertEquals("초기 주문 상태는 PENDING이어야 합니다", OrderStatus.PENDING, createdOrder.getStatus());
    }

    @Test
    public void 고객_주문_최소주문금액_미달() throws JsonProcessingException {
        //given
        OrderSaveRequest request = new OrderSaveRequest(1L, List.of(),1);
        List<OrderSaveRequest> requests = new ArrayList<>();
        requests.add(request);

        //when
        MinOrderAmountException exception = assertThrows(MinOrderAmountException.class, () -> {
            orderService.createOrder(authUser, store.getId(), requests);
        });

        //then
        assertEquals("최소주문금액 오류 비교","최소주문금액보다 주문 금액이 적습니다.",exception.getMessage());
    }

    @Test
    public void 사장_주문_수락() {
        //given
        OrderAcceptedRequest request = new OrderAcceptedRequest(order.getId(), OrderStatus.ACCEPTED);

        //when
        OrderAcceptedResponse response = orderOwnerService.updateOrderAccepted(authUser, store.getId(), request);

        //then
        assertEquals("주문 수락시 상태는 ACCEPTED이다.",OrderStatus.ACCEPTED, response.getStatus());
    }

    @Test
    public void 사장_주문_취소() {
        //given
        OrderAcceptedRequest request = new OrderAcceptedRequest(order.getId(), OrderStatus.CANCELED);

        //when
        OrderAcceptedResponse response = orderOwnerService.updateOrderAccepted(authUser, store.getId(), request);

        //then
        assertEquals("주문 취소시 상태는 CANCEL이다.",OrderStatus.CANCELED, response.getStatus());
    }

    @Test
    public void 사장_주문_수락여부_잘못_입력_예외() {
        //given
        OrderAcceptedRequest request = new OrderAcceptedRequest(order.getId(), OrderStatus.COMPLETED);

        //when
        OrderStatusNotForAcceptanceException exception = assertThrows(OrderStatusNotForAcceptanceException.class, () -> {
            orderOwnerService.updateOrderAccepted(authUser, store.getId(), request);
        });

        //then
        assertEquals("주문 수락 여부 잘못 입력하여 예외 발생","주문 수락 혹은 취소 하세요.", exception.getMessage());
    }
}
package com.example.nbc_outsourcingproject.domain.order.service;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.entity.OrderMenu;
import com.example.nbc_outsourcingproject.domain.order.enums.OrderStatus;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.order.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderOwnerServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private MyStoreCache myStoreCache;
    @InjectMocks
    private OrderOwnerService orderOwnerService;
    @InjectMocks
    private OrderService orderService;


    @Test
    public void 사장_주문_취소() {
        //given
        User user = new User("user@naver.com", "USER1234", "user", "seoul", UserRole.USER);
        Store store = new Store(user,"가게1", "주소1", 1000, "가게 소개 1", LocalTime.of(9, 0), LocalTime.of(23, 30));
        Order order = new Order(user,store);
        OrderMenu orderMenu = new OrderMenu(order, "김치찌개", 12000, 10, "스팸추가");
        orderRepository.save(order);

        Menu menu = new Menu(store,Category.MAIN,"김치찌개",12000,"김치찌개 맛있어요.");
        MenuOption menuOption = new MenuOption(menu,"스팸추가",3000);

        //when
        order.updateAccepted(OrderStatus.CANCELED);

        //then
        Order getOrder = orderRepository.findById(order.getId()).orElseThrow(
                () -> new OrderNotFoundException()
        );
        assertEquals("주문 취소시 상태는 CANCEL 이다.",OrderStatus.CANCELED, getOrder.getStatus());
    }
}
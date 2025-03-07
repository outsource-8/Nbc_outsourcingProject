package com.example.nbc_outsourcingproject.domain.store;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuService;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreDetailResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.store.service.StoreService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.List;

import static com.example.nbc_outsourcingproject.domain.auth.enums.UserRole.OWNER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuService menuService;

    @Test
    void 이름을_입력하여_가게를_다_건_조회한다() {
        //given
        User user = new User(1L, "사용자", UserRole.OWNER);
        String storeName = "test";
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);

        List<Store> storeList = List.of(
                new Store(user, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(user, "test2", "address2", 2000, "storeInfo2", LocalTime.of(18, 30, 45), LocalTime.of(14, 30, 45))
        );

        Page<Store> storesPage = new PageImpl<>(storeList, pageable, storeList.size());
        given(storeRepository.findStores(storeName, pageable)).willReturn(storesPage);

        //when
        Page<StoreResponse> response = storeService.getStores(storeName, page, size);

        //then
        assertNotNull(response);
        assertEquals(storeList.size(), response.getContent().size());

        for (StoreResponse storeResponse : response.getContent()) {
            assertTrue(storeResponse.getName().contains(storeName));  // storeName이 포함된 가게만 반환 되었는지 확인
        }
    }

    @Test
    void 이름을_입력하지_않으면_가게를_전체_조회한다() {
        //given
        User user = new User(1L, "사용자", UserRole.OWNER);
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);

        List<Store> storeList = List.of(
                new Store(user, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(user, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(user, "test2", "address2", 2000, "storeInfo2", LocalTime.of(18, 30, 45), LocalTime.of(14, 30, 45))
        );

        Page<Store> storesPage = new PageImpl<>(storeList, pageable, storeList.size());
        given(storeRepository.findStores(null, pageable)).willReturn(storesPage);

        //when
        Page<StoreResponse> response = storeService.getStores(null, page, size);

        //then
        assertNotNull(response);
        assertEquals(storeList.size(), response.getContent().size());
        List<String> storeNames = response.stream().map(StoreResponse::getName).toList();
        assertTrue(storeNames.contains("name1"));
        assertTrue(storeNames.contains("test1"));
        assertTrue(storeNames.contains("test2"));
    }

    @Test
    void 가게_아이디를_입력하여_가게_단건_조회와_메뉴_다건_조회를_한다() {
        //given
        Long storeId = 1L;
        User user = new User(1L, "email@email.com", OWNER);
        Store store = new Store(user, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30), LocalTime.of(14, 30));
        ReflectionTestUtils.setField(store, "id", storeId);
        given(storeRepository.findStoreBy(storeId)).willReturn(store);

        List<Menu> menuList = List.of(
                new Menu(store, Category.COFFEE, "아메리카노", 3000, "현대인 3대 영양소"),
                new Menu(store, Category.COFFEE, "카페라떼", 5000, "무지방 우유로 만든 라테"),
                new Menu(store, Category.COFFEE, "복숭아아이스티", 2500, "립톤 복숭아 아이스티가 근본")
        );
        List<MenuResponse> menuResponseList = menuList.stream().map(MenuResponse::from).toList();
        given(menuService.getMenu(storeId)).willReturn(menuResponseList);

        //when
        StoreDetailResponse response = storeService.getStoreDetail(storeId);

        //then
        assertNotNull(response);
        List<String> menuNames = response.getMenuResponseList().stream().map(MenuResponse::getName).toList();
        assertTrue(menuNames.contains("아메리카노"));
        assertTrue(menuNames.contains("카페라떼"));
        assertTrue(menuNames.contains("복숭아아이스티"));
    }
}

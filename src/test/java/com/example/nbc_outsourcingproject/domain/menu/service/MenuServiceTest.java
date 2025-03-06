package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.dto.OwnerMenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.menu.DuplicateMenuException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuOwnerService menuOwnerService;

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MyStoreCache myStoreCache;

    @Mock
    private StoreRepository storeRepository;


    @Test
    void null이_들어오면_이전_값으로() {
        Store store = new Store();
        Menu menu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        Long menuId = 1L;

        String category = Category.DESSERT.name();
        String name = "";
        Integer price = 13000;
        String info = "";

        ReflectionTestUtils.setField(menu, "id", menuId);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

        OwnerMenuResponse menuResponse = menuOwnerService.updateMenu(1L, 1L, menu.getId(), category, name, price, info);


        assertThat(menu.getName()).isEqualTo("메뉴");
        assertThat(menuResponse.getName()).isEqualTo("메뉴");

        assertThat(menu.getInfo()).isEqualTo("설명");
        assertThat(menuResponse.getInfo()).isEqualTo("설명");
    }

    @Test
    void storeId_name_으로_unique_중복_발생() {

        Store store = new Store();
        Menu existingMenu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        ReflectionTestUtils.setField(existingMenu, "id", 1L);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(existingMenu));

        menuOwnerService.updateMenu(1L, 1L, existingMenu.getId(), "MAIN", "메뉴", 12000, "설명");
        menuOwnerService.updateMenu(1L, 1L, existingMenu.getId(), "MAIN", "메뉴", 12000, "설명");

        DuplicateMenuException exception = new DuplicateMenuException();
        assertEquals("이미 존재하는 메뉴 입니다.", exception.getMessage());
    }

    @Test
    void 다른_store는_동일한_name_가능() {

        Store store = new Store();
        Menu existingMenu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        ReflectionTestUtils.setField(existingMenu, "id", 1L);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(existingMenu));

        OwnerMenuResponse ownerMenu1 = menuOwnerService.updateMenu(1L, 1L, existingMenu.getId(), "MAIN", "메뉴", 12000, "설명");
        OwnerMenuResponse ownerMenu2 = menuOwnerService.updateMenu(2L, 1L, existingMenu.getId(), "MAIN", "메뉴", 12000, "설명");
        OwnerMenuResponse ownerMenu3 = menuOwnerService.updateMenu(2L, 1L, existingMenu.getId(), "MAIN", "메뉴", 12000, "설명");

        // ownerMenu1과 ownerMenu2는 다른 가게
        assertEquals(ownerMenu1.getName(), ownerMenu2.getName());

        // ownerMenu2과 ownerMenu3는 같은 가게 = 예외 발생
        DuplicateMenuException exception = new DuplicateMenuException();
        assertEquals("이미 존재하는 메뉴 입니다.", exception.getMessage());
    }

    @Test
    void 손님은_isDeleted가_true인_메뉴_조회_불가능() {

        Store store = new Store();
        Menu existingMenu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        ReflectionTestUtils.setField(existingMenu, "id", 1L);
        ReflectionTestUtils.setField(store, "id", 1L);

        List<Menu> trueList = menuRepository.findByStoreIdAndIsDeleted(1L, true);
        List<MenuResponse> result = menuService.getMenu(1L);

        List<Menu> emptyList = Collections.emptyList();
        assertEquals(emptyList, trueList);
        assertEquals(emptyList, result);
    }

    @Test
    void 사장은_isDeleted가_true인_메뉴_조회_가능() {

        Store store = new Store();
        Menu existingMenu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        ReflectionTestUtils.setField(existingMenu, "id", 1L);
        ReflectionTestUtils.setField(store, "id", 1L);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(existingMenu));
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        List<Menu> trueList = menuRepository.findByStoreIdAndIsDeleted(1L, true);
        List<MenuResponse> userResult = menuService.getMenu(1L);
        List<OwnerMenuResponse> ownerResult = menuOwnerService.getMenus(1L, 1L, 1L);

        List<Menu> emptyList = Collections.emptyList();

        assertEquals(emptyList, trueList);

        assertEquals(emptyList, userResult);
        assertNotEquals(userResult, ownerResult);
    }

}
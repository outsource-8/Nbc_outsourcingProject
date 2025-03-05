package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

class MenuOwnerServiceTest {

    @Spy
    @InjectMocks
    private MenuOwnerService menuOwnerService;
    @Mock
    private MenuRepository menuRepository;


    // 실패한 테스트.... 어떻게 하지?
    @Test
    void null이_들어오면_이전_값으로() {
        Store store = new Store();
        Menu menu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");

        String category = Category.DESSERT.name();
        String name = null;
        Integer price = 13000;
        String info = null;

        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));

        MenuResponse menuResponse = menuOwnerService.updateMenu(1L, 1L, 2L, category, name, price, info);
        assertThat(menuResponse.getName()).isEqualTo("메뉴");
    }
}
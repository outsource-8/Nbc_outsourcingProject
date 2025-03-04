package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class MenuOwnerServiceTest {

    @InjectMocks
    private MenuOwnerService menuOwnerService;

    @Test
    void null이_들어오면_이전_값으로() {
        Menu menu = new Menu(null, Category.MAIN, "메뉴", 12000, "설명");
        String category = Category.DESSERT.name();
        String name = null;
        Integer price = 13000;
        String info = null;

        MenuResponse menuResponse = menuOwnerService.updateMenu(1L, 1L, 2L, category, name, price, info);
        assertThat(menuResponse.getName()).isEqualTo("메뉴");
    }
}
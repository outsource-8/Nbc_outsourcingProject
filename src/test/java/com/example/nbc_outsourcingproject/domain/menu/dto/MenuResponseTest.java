package com.example.nbc_outsourcingproject.domain.menu.dto;

import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuResponseTest {

    @Test
    void from() {
        //given & when
        Menu menu = new Menu(Category.MAIN, "메뉴1", 12000, null);
        MenuResponse from = MenuResponse.from(menu);

        //then
        assertThat(from.getPrice()).isEqualTo("12,000");
    }
}
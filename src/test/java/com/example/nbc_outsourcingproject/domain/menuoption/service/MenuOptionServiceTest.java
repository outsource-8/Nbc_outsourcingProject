package com.example.nbc_outsourcingproject.domain.menuoption.service;

import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuService;
import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import com.example.nbc_outsourcingproject.domain.menuoption.repository.MenuOptionRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.menu.MenuAlreadyDeletedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuOptionServiceTest {

    @InjectMocks
    private MenuOptionService menuOptionService;

    @Mock
    private MenuOptionRepository menuOptionRepository;

    @Mock
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MyStoreCache myStoreCache;

    @Test
    void null이_들어오면_이전_값으로() {
        Store store = new Store();
        Menu menu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");
        MenuOption option1 = new MenuOption(menu, "옵션", 100);
        MenuOption option2 = new MenuOption(menu, "옵션", 100);

        Long optionId1 = 1L;
        Long optionId2 = 2L;

        String text1 = "";
        String text2 = "수정";
        Integer price1 = 0;
        Integer price2 = 200;

        ReflectionTestUtils.setField(menu, "id", 1L);
        ReflectionTestUtils.setField(menu, "isDeleted", false);
        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));
        given(menuOptionRepository.findById(optionId1)).willReturn(Optional.of(option1));
        given(menuOptionRepository.findById(optionId2)).willReturn(Optional.of(option2));


        menuOptionService.updateOption(1L, 1L, optionId1, text1, price1);
        menuOptionService.updateOption(1L, 1L, optionId2, text2, price2);


        assertThat(option1.getText()).isEqualTo("옵션");
        assertThat(option2.getText()).isEqualTo("수정");

        assertThat(option1.getPrice()).isEqualTo(100);
        assertThat(option2.getPrice()).isEqualTo(200);
    }

    @Test
    void 삭제된_Menu에_옵션_불가능() {
        Store store = new Store();
        Menu menu = new Menu(store, Category.MAIN, "메뉴", 12000, "설명");
        MenuOption option = new MenuOption(menu, "옵션", 100);

        given(menuRepository.findById(anyLong())).willReturn(Optional.of(menu));
        given(menuRepository.findByMenuIdForStoreId(anyLong())).willReturn(1L);
        ReflectionTestUtils.setField(menu, "isDeleted", true);

        assertThrows(MenuAlreadyDeletedException.class, () -> {
            menuOptionService.createOption(1L, 1L, option.getText(), option.getPrice());
        });

        MenuAlreadyDeletedException exception = new MenuAlreadyDeletedException();
        assertEquals("이미 삭제한 메뉴 입니다.", exception.getMessage());
    }

}
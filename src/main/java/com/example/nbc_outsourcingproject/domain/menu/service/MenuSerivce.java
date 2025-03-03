package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.exception.DuplicateMenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.InvalidStoreMenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.MenuNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuSerivce {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;


    public void createMenu(Long storeId, Long authUser, String category, String name, int price, String info) {
        //TODO: storeId가 존재하는지 확인 필요 + 요청한 사람이 store 주인인지 확인 필요
        if (false) {
            //TODO: store 존재 x 예외 처리
        }

        try {
            Store store = storeRepository.findById(storeId);
            Category getCategory = Category.of(category);

            Menu menu = new Menu(store, getCategory, name, price, info);

            menuRepository.save(menu);

            //unique 중복으로 예외 발생 -> unique = store_id + name (한 가게에 동일한 메뉴가 있을 경우)
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMenuException();
        }
    }


    @Transactional
    public MenuResponse updateMenu(Long storeId, Long authUser, Long menuId, String category, String name, int price, String info) {
        //TODO: storeId가 존재하는지 확인 필요 + 요청한 사람이 store 주인인지 확인 필요
        if (false) {
            //TODO: store 존재 x 예외 처리
        }

        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        Menu updateMenu = modifiedMenu(getMenu, category, name, price, info);
        return MenuResponse.from(updateMenu);
    }


    @Transactional(readOnly = true)
    public List<MenuResponse> getMenus(Long storeId, Long authUser, Long menuId) {
        //TODO: storeId가 존재하는지 확인 필요 + 요청한 사람이 store 주인인지 확인 필요
        if (false) {
            //TODO: store 존재 x 예외 처리
        }

        // 가게의 전체 메뉴
        if (menuId == null) {
            List<Menu> storeMenuList = menuRepository.findByStoreId(storeId);
            return storeMenuList.stream().map(MenuResponse::from).toList();
        }

        // 특정 메뉴 선택
        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());

        // 선택한 메뉴가 해당 store의 메뉴가 아닐 경우 예외 처리
        if (!getMenu.getStore().equals(storeRepository.findById(storeId))) {
            throw new InvalidStoreMenuException();
        }

        return (List<MenuResponse>) MenuResponse.from(getMenu);
    }


    public void deleteMenu(Long storeId, Long authUser, Long menuId) {
        //TODO: storeId가 존재하는지 확인 필요 + 요청한 사람이 store 주인인지 확인 필요
        if (false) {
            //TODO: store 존재 x 예외 처리
        }

        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        getMenu.deleteMenu();
    }



    private Menu modifiedMenu(Menu getMenu, String category, String name, Integer price, String info) {
        Category updateCategory = Category.of(category);
        String updateName = name;
        int updatePrice = price;
        String updateInfo = info;

        // 요청에 입력하지 않은 값은 이전과 동일하게 유지
        if(category == null) {
            updateCategory = getMenu.getCategory();
        }
        if (name == null) {
            updateName = getMenu.getName();
        }
        if (price == null) {
            updatePrice = getMenu.getPrice();
        }
        if (info == null) {
            updateInfo = getMenu.getInfo();
        }

        // 업데이트
        return getMenu.update(updateCategory, updateName, updatePrice, updateInfo);
    }

}

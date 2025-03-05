package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.config.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.DuplicateMenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.InvalidStoreMenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.MenuNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.StoreNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuOwnerService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MyStoreCache myStoreCache;

    public void createMenu(Long storeId, Long userId, String category, String name, int price, String info) {
        myStoreCache.validateStoreOwner(userId, storeId);
        Store store = storeRepository.findById(storeId).get();

        try {

            Category getCategory = Category.of(category);
            Menu menu = new Menu(store, getCategory, name, price, info);

            menuRepository.save(menu);

            //unique 중복으로 예외 발생 -> unique = store_id + name (한 가게에 동일한 메뉴가 있을 경우)
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMenuException();
        }
    }


    @Transactional
    public MenuResponse updateMenu(Long storeId, Long userId, Long menuId, String category, String name, int price, String info) {
        myStoreCache.validateStoreOwner(userId, storeId);

        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        Menu updateMenu = modifiedMenu(getMenu, category, name, price, info);
        return MenuResponse.from(updateMenu);
    }


    @Transactional(readOnly = true)
    public List<MenuResponse> getMenus(Long storeId, Long userId, Long menuId) {
        myStoreCache.validateStoreOwner(userId, storeId);

        // 가게의 전체 메뉴
        if (menuId == null) {
            List<Menu> storeMenuList = menuRepository.findByStoreId(storeId);
            return storeMenuList.stream().map(MenuResponse::from).toList();
        }

        // 가게 단건 메뉴
        return Collections.singletonList(getMenu(storeId, menuId));
    }

    public void deleteMenu(Long storeId, Long userId, Long menuId) {
        myStoreCache.validateStoreOwner(userId, storeId);
        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        getMenu.deleteMenu();
    }

    private MenuResponse getMenu(Long storeId, Long menuId) {
        // 특정 메뉴 선택
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        // 선택한 메뉴가 해당 store의 메뉴가 아닐 경우 예외 처리
        if (!menu.getStore().equals(store)) {
            throw new InvalidStoreMenuException();
        }

        return MenuResponse.from(menu);
    }

    private Menu modifiedMenu(Menu getMenu, String category, String name, Integer price, String info) {
        Category updateCategory = Category.of(category);
        String updateName = name;
        int updatePrice = price;
        String updateInfo = info;

        // 요청에 입력하지 않은 값은 이전과 동일하게 유지
        if (category == null) {
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

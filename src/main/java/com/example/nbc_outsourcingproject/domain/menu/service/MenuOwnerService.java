package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.OwnerMenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.menu.DuplicateMenuException;
import com.example.nbc_outsourcingproject.global.exception.menu.InvalidStoreMenuException;
import com.example.nbc_outsourcingproject.global.exception.menu.MenuAlreadyDeletedException;
import com.example.nbc_outsourcingproject.global.exception.menu.MenuNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.store.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuOwnerService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MyStoreCache myStoreCache;

    public void createMenu(Long storeId, Long userId, String category, String name, int price, String info) {
        myStoreCache.validateStoreOwner(userId, storeId);
        Store store = getStore(storeId);

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
    public OwnerMenuResponse updateMenu(Long storeId, Long userId, Long menuId, String category, String name, int price, String info) {
        myStoreCache.validateStoreOwner(userId, storeId);

        Menu getMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        Menu updateMenu = modifiedMenu(getMenu, category, name, price, info);
        return OwnerMenuResponse.from(updateMenu);
    }


    @Transactional(readOnly = true)
    public List<OwnerMenuResponse> getMenus(Long storeId, Long userId, Long menuId) {
        myStoreCache.validateStoreOwner(userId, storeId);

        // 가게의 전체 메뉴
        if (menuId == null) {
            List<Menu> storeMenuList = menuRepository.findByStoreId(storeId);
            return storeMenuList.stream().map(OwnerMenuResponse::from).toList();
        }

        // 가게 단건 메뉴
        return Collections.singletonList(getMenu(storeId, menuId));
    }

    @Transactional
    public void deleteMenu(Long storeId, Long userId, Long menuId) {
        myStoreCache.validateStoreOwner(userId, storeId);
        Menu getMenu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        if(getMenu.getIsDeleted()){
            throw new MenuAlreadyDeletedException();
        }

        getMenu.deleteMenu();
    }

    private OwnerMenuResponse getMenu(Long storeId, Long menuId) {
        // 특정 메뉴 선택
        Menu menu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);

        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        // 선택한 메뉴가 해당 store의 메뉴가 아닐 경우 예외 처리
        if (!menu.getStore().equals(store)) {
            throw new InvalidStoreMenuException();
        }

        return OwnerMenuResponse.from(menu);
    }

    private Menu modifiedMenu(Menu getMenu, String category, String name, Integer price, String info) {
        String updateCategory = String.valueOf(getMenu.getCategory());
        String updateName = getMenu.getName();
        int updatePrice = getMenu.getPrice();
        String updateInfo = getMenu.getInfo();

        // 요청에 입력하지 않은 값은 이전과 동일하게 유지
        if (!category.isBlank()) {
            updateCategory = category;
        }
        if (!name.isBlank()) {
            updateName = name;
        }
        if (price != 0) {
            updatePrice = price;
        }
        if (!info.isBlank()) {
            updateInfo = info;
        }

        // 업데이트
        return getMenu.update(Category.valueOf(updateCategory), updateName, updatePrice, updateInfo);
    }

    private Store getStore(Long storeId) {
        return storeRepository.findByIdAndIsShutDown(storeId, false).orElseThrow(StoreNotFoundException::new);
    }
}

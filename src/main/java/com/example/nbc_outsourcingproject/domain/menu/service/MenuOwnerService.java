package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.*;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuOwnerService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;


    public void createMenu(Long storeId, Long authUserId, String category, String name, int price, String info) {
        storeValidate(storeId, authUserId);
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
    public MenuResponse updateMenu(Long storeId, Long authUserId, Long menuId, String category, String name, int price, String info) {
        storeValidate(storeId, authUserId);

        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        Menu updateMenu = modifiedMenu(getMenu, category, name, price, info);
        return MenuResponse.from(updateMenu);
    }


    @Transactional(readOnly = true)
    public List<MenuResponse> getMenus(Long storeId, Long authUserId, Long menuId) {
        storeValidate(storeId, authUserId);

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


    public void deleteMenu(Long storeId, Long authUserId, Long menuId) {
        storeValidate(storeId, authUserId);
        Menu getMenu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        getMenu.deleteMenu();
    }


    private void storeValidate(Long storeId, Long authUserId) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException();
        }

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        User user = userRepository.findById(authUserId).orElseThrow();

        if (!storeRepository.existsByIdAndUser(store, user)) {
            throw new InvalidStoreOwner();
        }
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

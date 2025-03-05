package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenu(Long storeId) {
        List<Menu> storeMenuList = menuRepository.findByStoreId(storeId);
        return storeMenuList.stream().map(MenuResponse::from).toList();
    }
}

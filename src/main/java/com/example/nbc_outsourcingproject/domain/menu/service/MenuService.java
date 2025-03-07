package com.example.nbc_outsourcingproject.domain.menu.service;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenu(Long storeId) {
        List<Menu> storeMenuList = menuRepository.findByStoreIdAndIsDeleted(storeId, false);
        return storeMenuList.stream().map(MenuResponse::from).toList();
    }
}
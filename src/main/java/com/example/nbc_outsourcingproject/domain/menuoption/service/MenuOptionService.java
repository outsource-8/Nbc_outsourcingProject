package com.example.nbc_outsourcingproject.domain.menuoption.service;

import com.example.nbc_outsourcingproject.config.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.InvalidStoreMenuException;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.MenuNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.example.nbc_outsourcingproject.domain.menuoption.entity.MenuOption;
import com.example.nbc_outsourcingproject.domain.menuoption.exception.details.MenuOptionNotFoundException;
import com.example.nbc_outsourcingproject.domain.menuoption.repository.MenuOptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuOptionService {

    private final MenuRepository menuRepository;
    private final MenuOptionRepository optionRepository;
    private final MyStoreCache myStoreCache;

    private final ObjectMapper objectMapper;

    //TODO: 구현 후 시간이 남으면 component service 생성하여 다른 entity 가져오기
    @Transactional
    public void createOption(Long userId, Long menuId, String text, Integer price) {
        Menu menu = validateMenuOfStore(userId, menuId);
        MenuOption menuOption = new MenuOption(menu, text, price);
        optionRepository.save(menuOption);
    }

    @Transactional(readOnly = true)
    public List<MenuOptionResponse> getOptions(Long userId, Long menuId, Long optionId) {
        validateMenuOfStore(userId, menuId);
        if (optionId == null) {
            List<MenuOption> menuOptionList = optionRepository.findByMenuId(menuId);
            return menuOptionList.stream().map(MenuOptionResponse::from).toList();
        }
        return Collections.singletonList(getOption(optionId, menuId));
    }


    @Transactional
    public MenuOptionResponse updateOption(Long userId, Long menuId, Long optionId, String text, Integer price) {
        Menu menu = validateMenuOfStore(userId, menuId);

        MenuOption option = optionRepository.findById(optionId).orElseThrow(MenuOptionNotFoundException::new);
        MenuOption updateOption = modifiedOption(option, text, price);

        return MenuOptionResponse.of(menu.getName(), updateOption.getText(), updateOption.getPrice());
    }


    public void deleteOption(Long userId, Long menuId, Long optionId) {
        validateMenuOfStore(userId, menuId);
        optionRepository.deleteById(optionId);
    }


    private MenuOptionResponse getOption(Long menuId, Long optionId) {
        // 가져오려는 옵션이 존재하지 않을 경우
        if(! optionRepository.existsById(optionId)){
            throw new MenuOptionNotFoundException();
        }

        // 가져오려는 옵션이 menu에 포함된 옵션이 아닐 경우
        MenuOption option = optionRepository.findByIdAndMenu_Id(optionId, menuId).orElseThrow(InvalidStoreMenuException::new);

        return MenuOptionResponse.from(option);
    }

    private MenuOption modifiedOption(MenuOption option, String text, Integer price) {
        String updateText = text;
        Integer updatePrice = price;

        if (text == null) {
            updateText = option.getText();
        }
        if (price == null) {
            updatePrice = option.getPrice();
        }

        return option.updateOption(updateText, updatePrice);
    }

    private Menu validateMenuOfStore(Long userId, Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
        Long storeId = menuRepository.findByMenuIdForStoreId(menuId);
        myStoreCache.validateStoreOwner(userId, storeId);

        return menu;
    }



}

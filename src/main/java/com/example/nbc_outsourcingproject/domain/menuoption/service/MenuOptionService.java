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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuOptionService {

    private final MenuRepository menuRepository;
    private final MenuOptionRepository optionRepository;
    private final MyStoreCache myStoreCache;

    //TODO: 구현 후 시간이 남으면 component service 생성하여 다른 entity 가져오기
    @Transactional
    public void createOption(Long userId, Long menuId, String text, Integer price) {
        Menu menu = validateMenuOfStore(userId, menuId);
        MenuOption menuOption = new MenuOption(menu, text, price);
        optionRepository.save(menuOption);
    }


    public List<MenuOptionResponse> getOptions(Long userId, Long menuId, Long optionId) {
        validateMenuOfStore(userId, menuId);
        if (optionId == null) {
            return optionRepository.findByMenuId(menuId).stream().map(MenuOptionResponse::from).toList();
        }

        // 가져오려는 옵션이 존재하지 않을 경우
        if(optionRepository.existsById(optionId)){
            throw new MenuOptionNotFoundException();
        }

        // 가져오려는 옵션이 menu에 포함된 옵션이 아닐 경우
        MenuOption option = optionRepository.findByIdAndMenu_Id(optionId, menuId).orElseThrow(InvalidStoreMenuException::new);
        return (List<MenuOptionResponse>) MenuOptionResponse.from(option);
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

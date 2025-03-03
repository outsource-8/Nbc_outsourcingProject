package com.example.nbc_outsourcingproject.domain.menu.controller;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuRequest;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuUpdateRequest;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<String> createMenu(@PathVariable Long storeId, @Auth AuthUser authUser,
                                             @Valid @RequestBody MenuRequest menuRequest) {
        menuService.createMenu(storeId, authUser.getId(), menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>("등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long storeId,
                                                   @Auth AuthUser authUser,
                                                   @PathVariable Long menuId,
                                                   @Valid @RequestBody MenuUpdateRequest menuRequest) {
        MenuResponse menuResponse = menuService.updateMenu(storeId, authUser.getId(), menuId, menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/menus")
    public ResponseEntity<List<MenuResponse>> getMenus(@PathVariable Long storeId, @Auth AuthUser authUser, @RequestParam(required = false) Long menuId) {
        List<MenuResponse> menus = menuService.getMenus(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId, @Auth AuthUser authUser, @PathVariable Long menuId) {
        menuService.deleteMenu(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>("메뉴를 삭제하였습니다.", HttpStatus.OK);
    }
}

package com.example.nbc_outsourcingproject.domain.menu.controller;

import com.example.nbc_outsourcingproject.domain.menu.dto.MenuRequest;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuUpdateRequest;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuSerivce;
import jakarta.servlet.http.HttpServletRequest;
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

    private final MenuSerivce menuSerivce;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<String> createMenu(@PathVariable Long storeId,
                                             @Valid @RequestBody MenuRequest menuRequest) {
        menuSerivce.createMenu(storeId, menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>("등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long storeId,
                                                   @PathVariable Long menuId,
                                                   @Valid @RequestBody MenuUpdateRequest menuRequest) {
        MenuResponse menuResponse = menuSerivce.updateMenu(storeId, menuId, menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/menus")
    public ResponseEntity<List<MenuResponse>> getMenus(@PathVariable Long storeId, @RequestParam(required = false) Long menuId) {
        List<MenuResponse> menus = menuSerivce.getMenus(storeId, menuId);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId, @PathVariable Long menuId) {
        menuSerivce.deleteMenu(storeId, menuId);
        return new ResponseEntity<>("메뉴를 삭제하였습니다.", HttpStatus.OK);
    }
}

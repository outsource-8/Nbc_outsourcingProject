package com.example.nbc_outsourcingproject.domain.menu.controller;

import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuRequest;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuUpdateRequest;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/stores")
@RequiredArgsConstructor
public class MenuOwnerController {

    private final MenuOwnerService menuOwnerService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<String> createMenu(@PathVariable Long storeId, @Auth AuthUser authUser,
                                             @Valid @RequestBody MenuRequest menuRequest) {
        menuOwnerService.createMenu(storeId, authUser.getId(), menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>("등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long storeId,
                                                   @Auth AuthUser authUser,
                                                   @PathVariable Long menuId,
                                                   @Valid @RequestBody MenuUpdateRequest menuRequest) {
        MenuResponse menuResponse = menuOwnerService.updateMenu(storeId, authUser.getId(), menuId, menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/menus")
    public ResponseEntity<List<MenuResponse>> getMenus(@PathVariable Long storeId, @Auth AuthUser authUser, @RequestParam(required = false) Long menuId) {
        List<MenuResponse> menus = menuOwnerService.getMenus(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId, @Auth AuthUser authUser, @PathVariable Long menuId) {
        menuOwnerService.deleteMenu(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>("메뉴를 삭제하였습니다.", HttpStatus.OK);
    }
}

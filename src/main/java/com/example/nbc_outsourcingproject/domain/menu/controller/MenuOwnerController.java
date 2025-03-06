package com.example.nbc_outsourcingproject.domain.menu.controller;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuRequest;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuResponse;
import com.example.nbc_outsourcingproject.domain.menu.dto.MenuUpdateRequest;
import com.example.nbc_outsourcingproject.domain.menu.service.MenuOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/stores")
@RequiredArgsConstructor
@Tag(name = "사장님이 사용할 메뉴 관리 API")
public class MenuOwnerController {

    private final MenuOwnerService menuOwnerService;

    @PostMapping("/{storeId}/menus")
    @Operation(summary = "메뉴 추가")
    public ResponseEntity<String> createMenu(@PathVariable Long storeId, @Parameter(hidden = true) @Auth AuthUser authUser,
                                             @Valid @RequestBody MenuRequest menuRequest) {
        menuOwnerService.createMenu(storeId, authUser.getId(), menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>("등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    @Operation(summary = "메뉴 수정")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long storeId,
                                                   @Parameter(hidden = true) @Auth AuthUser authUser,
                                                   @PathVariable Long menuId,
                                                   @Valid @RequestBody MenuUpdateRequest menuRequest) {
        MenuResponse menuResponse = menuOwnerService.updateMenu(storeId, authUser.getId(), menuId, menuRequest.getCategory(), menuRequest.getName(), menuRequest.getPrice(), menuRequest.getInfo());
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/menus")
    @Operation(summary = "메뉴 반환 - param에 menuId 입력 시 특정 메뉴 반환")
    public ResponseEntity<List<MenuResponse>> getMenus(@PathVariable Long storeId, @Parameter(hidden = true) @Auth AuthUser authUser,
                                                       @RequestParam(required = false) Long menuId) {
        List<MenuResponse> menus = menuOwnerService.getMenus(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    @Operation(summary = "메뉴 삭제 - isDeleted 상태 변경")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId, @Parameter(hidden = true) @Auth AuthUser authUser,
                                             @PathVariable Long menuId) {
        menuOwnerService.deleteMenu(storeId, authUser.getId(), menuId);
        return new ResponseEntity<>("메뉴를 삭제하였습니다.", HttpStatus.OK);
    }
}

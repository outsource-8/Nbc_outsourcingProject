package com.example.nbc_outsourcingproject.domain.menuoption.controller;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionRequest;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.example.nbc_outsourcingproject.domain.menuoption.service.MenuOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/menus")
@RequiredArgsConstructor
@Tag(name = "메뉴에 대한 옵션 관리 API")
public class MenuOptionController {

    private final MenuOptionService optionService;

    @PostMapping("/{menuId}/options")
    @Operation(summary = "메뉴 옵션 추가")
    public ResponseEntity<String> createOption(@Parameter(hidden = true) @Auth AuthUser authUser,
                                               @PathVariable("menuId") Long menuId,
                                               @RequestBody MenuOptionRequest optionRequest) {
        optionService.createOption(authUser.getId(), menuId, optionRequest.getText(), optionRequest.getPrice());
        return new ResponseEntity<>("옵션이 등록되었습니다.", HttpStatus.CREATED);
    }


    @GetMapping("/{menuId}/options")
    @Operation(summary = "옵션 정보 조회 - param에 optionId 입력 시 특정 옵션 반환")
    public ResponseEntity<List<MenuOptionResponse>> getOptions(@Parameter(hidden = true) @Auth AuthUser authUser,
                                                               @PathVariable("menuId") Long menuId,
                                                               @RequestParam(required = false) Long optionId) {
        List<MenuOptionResponse> options = optionService.getOptions(authUser.getId(), menuId, optionId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }


    @PutMapping(("/{menuId}/options/{optionId}"))
    @Operation(summary = "옵션 정보 수정")
    public ResponseEntity<MenuOptionResponse> updateOption(@Parameter(hidden = true) @Auth AuthUser authUser,
                                                           @PathVariable("menuId") Long menuId, @PathVariable("optionId") Long optionId,
                                                           @RequestBody MenuOptionRequest optionRequest) {
        MenuOptionResponse option = optionService.updateOption(authUser.getId(), menuId, optionId, optionRequest.getText(), optionRequest.getPrice());
        return new ResponseEntity<>(option, HttpStatus.OK);
    }


    @DeleteMapping(("/{menuId}/options/{optionId}"))
    @Operation(summary = "옵션 삭제")
    public ResponseEntity<String> deleteOption(@Parameter(hidden = true) @Auth AuthUser authUser,
                                               @PathVariable("menuId") Long menuId,
                                               @PathVariable("optionId") Long optionId) {
        optionService.deleteOption(authUser.getId(), menuId, optionId);
        return new ResponseEntity<>("옵션이 삭제되었습니다.", HttpStatus.OK);
    }

}

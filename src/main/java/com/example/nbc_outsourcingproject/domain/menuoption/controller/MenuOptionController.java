package com.example.nbc_outsourcingproject.domain.menuoption.controller;

import com.example.nbc_outsourcingproject.domain.common.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionRequest;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.example.nbc_outsourcingproject.domain.menuoption.service.MenuOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/menus")
@RequiredArgsConstructor
public class MenuOptionController {

    private final MenuOptionService optionService;

    @PostMapping("/{menuId}/options")
    public ResponseEntity<String> createOption(@Auth AuthUser authUser,
                                               @PathVariable("menuId") Long menuId,
                                               @RequestBody MenuOptionRequest optionRequest) {
        optionService.createOption(authUser.getId(), menuId, optionRequest.getText(), optionRequest.getPrice());
        return new ResponseEntity<>("옵션이 등록되었습니다.", HttpStatus.CREATED);
    }


    @GetMapping("/{menuId}/options")
    public ResponseEntity<List<MenuOptionResponse>> getOptions(@Auth AuthUser authUser,
                                                               @PathVariable("menuId") Long menuId,
                                                               @RequestParam(required = false) Long optionId) {
        List<MenuOptionResponse> options = optionService.getOptions(authUser.getId(), menuId, optionId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }


    @PutMapping(("/{menuId}/options/{optionId}"))
    public ResponseEntity<MenuOptionResponse> updateOption(@Auth AuthUser authUser,
                                                           @PathVariable("menuId") Long menuId, @PathVariable("optionId") Long optionId,
                                                           @RequestBody MenuOptionRequest optionRequest) {
        MenuOptionResponse option = optionService.updateOption(authUser.getId(), menuId, optionId, optionRequest.getText(), optionRequest.getPrice());
        return new ResponseEntity<>(option, HttpStatus.OK);
    }


    @DeleteMapping(("/{menuId}/options/{optionId}"))
    public ResponseEntity<String> deleteOption(@Auth AuthUser authUser, @PathVariable("menuId") Long menuId, @PathVariable("optionId") Long optionId) {
        optionService.deleteOption(authUser.getId(), menuId, optionId);
        return new ResponseEntity<>("옵션이 삭제되었습니다.", HttpStatus.OK);
    }

}

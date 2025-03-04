package com.example.nbc_outsourcingproject.domain.menuoption.controller;

import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionRequest;
import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import com.example.nbc_outsourcingproject.domain.menuoption.service.MenuOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class MenuOptionController {

    private final MenuOptionService optionService;

    @PostMapping("/menus/{menuId}/options")
    public ResponseEntity<String> createOption(@PathVariable("menuId") Long menuId,
                                               @RequestBody MenuOptionRequest optionRequest) {

    }


    @GetMapping("/menus/{menuId}/options")
    public ResponseEntity<List<MenuOptionResponse>> getOptions(@PathVariable("menuId") Long menuId,
                                                               @RequestParam Long optionId) {

    }


    @PutMapping(("/menus/{menuId}/options/{optionId}"))
    public ResponseEntity<MenuOptionResponse> updateOption(@PathVariable("menuId") Long menuId,@PathVariable("optionId") Long optionId,
                                               @RequestBody MenuOptionRequest optionRequest) {

    }


    @DeleteMapping(("/menus/{menuId}/options/{optionId}"))
    public ResponseEntity<String> deleteOption(@PathVariable("menuId") Long menuId,@PathVariable("optionId") Long optionId) {

    }

}

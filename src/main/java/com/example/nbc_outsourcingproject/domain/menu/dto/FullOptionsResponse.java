package com.example.nbc_outsourcingproject.domain.menu.dto;

import com.example.nbc_outsourcingproject.domain.menuoption.dto.MenuOptionResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class FullOptionsResponse {

//    private String category;
    private String name;
    private Integer price;
    private String info;
    private List<MenuOptionResponse> options;

    public FullOptionsResponse(String category, String name, Integer price, String info, List<MenuOptionResponse> options) {

    }
}

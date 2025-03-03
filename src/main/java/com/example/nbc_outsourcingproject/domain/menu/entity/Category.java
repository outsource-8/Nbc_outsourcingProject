package com.example.nbc_outsourcingproject.domain.menu.entity;

import com.example.nbc_outsourcingproject.domain.menu.exception.CategoryNotFoundException;

import java.util.Arrays;

public enum Category {
    MAIN,
    SINGLE,
    SET,
    SIDE,
    COFFEE,
    BEVERAGE,
    DESSERT;

    public static Category of(String selectCategory) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name().equals(selectCategory))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException());
    }
}

package com.example.nbc_outsourcingproject.domain.order.enums;

public enum OrderStatus {

    ACCEPTED(0,"Y"),
    CANCELED(0,"N"),

    PENDING(1,"1"),
    COOKING(2,"2"),
    DELIVERING(3,"3"),
    COMPLETED(4,"4");

    private int code;
    private String message;

    OrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.example.nbc_outsourcingproject.global.exception.errorcode;

import com.example.nbc_outsourcingproject.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문이 존재하지 않습니다."),
    ORDER_ONLY_CUSTOMER(HttpStatus.BAD_REQUEST, "고객만 주문할 수 없습니다."),
    ORDER_AMOUNT_BELOW_MINIMUM(HttpStatus.BAD_REQUEST, "최소주문금액보다 주문 금액이 적습니다."),
    INVALID_ORDER_ACCEPTANCE_STATUS(HttpStatus.BAD_REQUEST, "주문 수락 혹은 취소 하세요.");

    private final HttpStatus httpStatus;
    private final String message;

    OrderErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


    public String getName(){
        return this.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

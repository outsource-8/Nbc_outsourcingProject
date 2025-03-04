package com.example.nbc_outsourcingproject.domain.common.exception;

import com.example.nbc_outsourcingproject.domain.menu.exception.MenuException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MenuException.class)
    public ResponseEntity<ErrorResponse> handleMenuException(MenuException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getMenuExceptionCode().name(), e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
    }
}

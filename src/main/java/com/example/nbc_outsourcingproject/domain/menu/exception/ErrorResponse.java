package com.example.nbc_outsourcingproject.domain.menu.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private String exception;
    private HttpStatus status;
    private String message;


    public static ErrorResponse of(String exceptionCode, HttpStatus httpStatus, String message){
        return ErrorResponse.builder()
                .exception(exceptionCode)
                .status(httpStatus)
                .message(message)
                .build();
    }
}

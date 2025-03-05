package com.example.nbc_outsourcingproject.domain.common.exception.details;

import com.example.nbc_outsourcingproject.domain.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OutsourcingException extends RuntimeException{
    private HttpStatus httpStatus;
    private ErrorCode errorCode;

    public OutsourcingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
    }
}

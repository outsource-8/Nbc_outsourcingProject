package com.example.nbc_outsourcingproject.global.exception.review;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.ReviewErrorCode;

public class ReviewNotFoundException extends OutsourcingException {
    public ReviewNotFoundException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}

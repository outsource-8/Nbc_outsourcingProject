package com.example.nbc_outsourcingproject.domain.review.dto.request;

import lombok.Getter;

@Getter
public class UpdateReviewRequest {

    private int rating;
    private String content;
}

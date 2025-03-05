package com.example.nbc_outsourcingproject.domain.review.dto.request;

import lombok.Getter;

@Getter
public class UpdateReviewRequest {

    private Integer rating;
    private String content;
}

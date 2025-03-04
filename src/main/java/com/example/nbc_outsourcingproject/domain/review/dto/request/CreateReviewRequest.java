package com.example.nbc_outsourcingproject.domain.review.dto.request;

import lombok.Getter;

@Getter
public class CreateReviewRequest {

    private int rating;
    private String content;
}

package com.example.nbc_outsourcingproject.domain.review.dto.response;

import lombok.Getter;

@Getter
public class ReviewResponse {

    private final Long id;
    private final int rating;
    private final String content;
    private final Long orderId;

}

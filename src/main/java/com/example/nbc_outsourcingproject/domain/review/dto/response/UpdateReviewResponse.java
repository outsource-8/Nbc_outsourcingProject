package com.example.nbc_outsourcingproject.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateReviewResponse {

    private final Long id;
    private final Integer rating;
    private final String content;
    private final Long orderId;
    private final Long userId;
    private final Long storeId;

    @Builder
    public UpdateReviewResponse(
            Long id,
            Integer rating,
            String content,
            Long orderId,
            Long userId,
            Long storeId
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
    }
}

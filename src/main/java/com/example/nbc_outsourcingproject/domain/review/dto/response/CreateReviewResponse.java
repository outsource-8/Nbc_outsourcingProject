package com.example.nbc_outsourcingproject.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateReviewResponse {

    private final Long id;
    private final int rating;
    private final String content;
    private final Long order_id;
    private final Long user_id;
    private final Long store_id;

    @Builder
    public CreateReviewResponse(
            Long id,
            int rating,
            String content,
            Long order_id,
            Long user_id,
            Long store_id
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.order_id = order_id;
        this.user_id = user_id;
        this.store_id = store_id;
    }
}

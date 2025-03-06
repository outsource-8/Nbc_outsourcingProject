package com.example.nbc_outsourcingproject.domain.review.dto.response;

import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadReviewResponse {

    private final Long id;
    private final Integer rating;
    private final String content;
    private final Long orderId;
    private final Long userId;
    private final Long storeId;

    @Builder
    public ReadReviewResponse(
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

    public static ReadReviewResponse toDto(Review review) {
        return ReadReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .orderId(review.getOrder().getId())
                .userId(review.getUser().getId())
                .storeId(review.getStore().getId())
                .build();
    }
}

package com.example.nbc_outsourcingproject.domain.review.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    @NotNull(message = "별점을 입력해주세요.")
    @Min(value = 0, message = "최소 별점은 0보다 커야 합니다.")
    private Integer rating;

    @NotBlank(message = "리뷰를 작성해 주세요.")
    @Size(max = 100, message = "리뷰 내용은 최대 100자 입니다.")
    private String content;
}

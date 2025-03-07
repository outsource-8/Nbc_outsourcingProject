package com.example.nbc_outsourcingproject.domain.review.controller;


import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.request.UpdateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/{storeId}/reviews")
@Tag(name = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    @Operation(summary = "리뷰 생성")
    public ResponseEntity<CreateReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            @PathVariable Long storeId,
            @RequestParam Long orderId,
            @Parameter(hidden = true) @Auth AuthUser user
            ) {

        return new ResponseEntity<>(reviewService.createReview(request, user, storeId, orderId), HttpStatus.CREATED);
    }

    // 리뷰 조회(가게 기준)
    @GetMapping
    @Operation(summary = "리뷰 조회")
    public ResponseEntity<Page<ReadReviewResponse>> getReviews(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ReadReviewResponse> response = reviewService.getReviewsByStoreId(storeId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 리뷰 조회(가게내 별점 기준)
    @GetMapping("/rating")
    @Operation(summary = "별점 리뷰 조회")
    public ResponseEntity<Page<ReadReviewResponse>> getReviewsByRating(
            @PathVariable Long storeId,
            @RequestParam Integer rating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        if (rating == null || rating < 1 || rating > 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<ReadReviewResponse> response = reviewService.getReviewsByRating(storeId, rating, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request,
            @Parameter(hidden = true) @Auth AuthUser user
    ) {
        return new ResponseEntity<>(reviewService.updateReview(storeId, reviewId, user, request), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @Parameter(hidden = true) @Auth AuthUser user
    ) {
        reviewService.deleteReview(storeId, reviewId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

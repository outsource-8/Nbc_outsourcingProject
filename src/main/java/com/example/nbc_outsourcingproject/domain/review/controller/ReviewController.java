package com.example.nbc_outsourcingproject.domain.review.controller;


import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.annotation.Auth;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.request.UpdateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/{storeId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping(params = "orderId")
    public ResponseEntity<CreateReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            @PathVariable Long storeId,
            @RequestParam Long orderId,
            @Auth AuthUser user
            ) {

        return new ResponseEntity<>(reviewService.createReview(request, user, storeId, orderId), HttpStatus.CREATED);
    }

    // 리뷰 조회(가게 기준)
    @GetMapping
    public ResponseEntity<Page<ReadReviewResponse>> getReviews(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<ReadReviewResponse> response = reviewService.getReviewsByStoreId(storeId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 리뷰 조회(가게내 별점 기준)
    @GetMapping(params = "rating")
    public ResponseEntity<Page<ReadReviewResponse>> getReviewsByRating(
            @PathVariable Long storeId,
            @RequestParam Integer rating,
            @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        if (rating == null || rating < 1 || rating > 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<ReadReviewResponse> response = reviewService.getReviewsByRating(storeId, rating, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request,
            @Auth AuthUser user
    ) {
        return new ResponseEntity<>(reviewService.updateReview(storeId, reviewId, user, request), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @Auth AuthUser user
    ) {
        reviewService.deleteReview(storeId, reviewId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

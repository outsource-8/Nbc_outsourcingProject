package com.example.nbc_outsourcingproject.domain.review.controller;


import com.example.nbc_outsourcingproject.config.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.request.UpdateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.service.ReviewService;
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

    private final JwtUtil jwtUtil;

    // 리뷰 생성
    @PostMapping(params = "orderId")
    public ResponseEntity<CreateReviewResponse> createReview(
            @RequestBody CreateReviewRequest request,
            @PathVariable Long storeId,
            @RequestParam Long orderId,
            @RequestHeader("Authorization") String token
    ) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long userId = jwtUtil.extractUserId(token);
        return new ResponseEntity<>(reviewService.createReview(request, userId, storeId, orderId), HttpStatus.CREATED);
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
            @RequestParam int rating,
            @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<ReadReviewResponse> response = reviewService.getReviewsByRating(storeId, rating, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewResponse> updateReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @RequestBody UpdateReviewRequest request,
            @RequestHeader("Authorization") String token
    ) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long userId = jwtUtil.extractUserId(token);
        return new ResponseEntity<>(reviewService.updateReview(storeId, reviewId, userId, request.getRating(), request.getContent()), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long storeId,
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String token
    ) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long userId = jwtUtil.extractUserId(token);
        reviewService.deleteReview(storeId, reviewId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

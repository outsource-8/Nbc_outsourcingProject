package com.example.nbc_outsourcingproject.domain.review.controller;


import com.example.nbc_outsourcingproject.config.jwt.JwtUtil;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
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

    @GetMapping
    public ResponseEntity<Page<ReadReviewResponse>> getReviews(
            @PathVariable Long storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReadReviewResponse> response = reviewService.getReviewsByStoreId(storeId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

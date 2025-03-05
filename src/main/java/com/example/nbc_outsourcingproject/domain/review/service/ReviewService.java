package com.example.nbc_outsourcingproject.domain.review.service;

import com.example.nbc_outsourcingproject.domain.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.request.UpdateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import com.example.nbc_outsourcingproject.domain.review.repository.ReviewRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.service.StoreService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final OrderService orderService;

    // review 생성
    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request, Long userId, Long storeId, Long orderId) {
        User user = userService.getUserEntityById(userId);
        Store store = storeService.getStoreEntityById(storeId);
        Order order = orderService.getOrderEntityById(orderId);

        Review review = reviewRepository.save(
                Review.builder()
                        .rating(request.getRating())
                        .content(request.getContent())
                        .order(order)
                        .user(user)
                        .store(store)
                        .build()
        );

        return CreateReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .orderId(review.getOrder().getId())
                .userId(review.getUser().getId())
                .storeId(review.getStore().getId())
                .build();
    }

    // 리뷰 조회(가게 기준)
    @Transactional(readOnly = true)
    public Page<ReadReviewResponse> getReviewsByStoreId(Long storeId, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);

        return reviews
                .map(ReadReviewResponse::toDto);
    }

    // 리뷰 조회(가게내 별점 기준)
    @Transactional(readOnly = true)
    public Page<ReadReviewResponse> getReviewsByRating(Long storeId, int rating, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findByStoreIdAndRating(storeId, rating, pageable);

        return reviews
                .map(ReadReviewResponse::toDto);
    }

    // 리뷰 수정
    @Transactional
    public UpdateReviewResponse updateReview(Long storeId, Long reviewId, Long userId, int newRating, String newContent) {
        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId);

        if (!userId.equals(review.getUser().getId())) {
            throw new UnauthorizedException("작성자가 아닙니다.");
        }

        review.updateReview(newRating, newContent);
        return UpdateReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .orderId(review.getOrder().getId())
                .userId(review.getUser().getId())
                .storeId(review.getStore().getId())
                .build();
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long storeId, Long reviewId, Long userId) {

        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId);

        if (!userId.equals(review.getUser().getId())) {
            throw new UnauthorizedException("작성자가 아닙니다.");
        }

        review.delete();
    }
}

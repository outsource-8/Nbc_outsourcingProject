package com.example.nbc_outsourcingproject.domain.review.service;

import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import com.example.nbc_outsourcingproject.domain.review.repository.ReviewRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    // review 생성
    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request, AuthUser user, Long storeId, Long orderId) {

        User users = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalStateException("존재하지 않는 가게입니다."));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("존재하지 않는 주문입니다."));

        Review review = reviewRepository.save(
                Review.builder()
                        .rating(request.getRating())
                        .content(request.getContent())
                        .order(order)
                        .user(users)
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
    public UpdateReviewResponse updateReview(Long storeId, Long reviewId, AuthUser user, int newRating, String newContent) {
        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId);

        if (!user.getId().equals(review.getUser().getId())) {
            throw new IllegalStateException("작성자가 아닙니다.");
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
    public void deleteReview(Long storeId, Long reviewId, AuthUser user) {

        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId);

        if (!user.getId().equals(review.getUser().getId())) {
            throw new IllegalStateException("작성자가 아닙니다.");
        }

        review.delete();
    }
}

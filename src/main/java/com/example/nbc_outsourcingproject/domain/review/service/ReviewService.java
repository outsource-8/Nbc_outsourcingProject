package com.example.nbc_outsourcingproject.domain.review.service;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.order.dto.OrderResponse;
import com.example.nbc_outsourcingproject.domain.order.entity.Order;
import com.example.nbc_outsourcingproject.domain.order.repository.OrderRepository;
import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.request.UpdateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.ReadReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.dto.response.UpdateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import com.example.nbc_outsourcingproject.domain.review.repository.ReviewRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import com.example.nbc_outsourcingproject.global.exception.order.OrderNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.review.ReviewNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.store.StoreNotFoundException;
import com.example.nbc_outsourcingproject.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        User users = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

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
    public Page<ReadReviewResponse> getReviewsByStoreId(Long storeId, int page, int size) {

//        Page<Review> reviews = reviewRepository.findByStoreId(storeId, page, size);

        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;

        // pageable 객체 생성일 기준 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        // Page 조회
        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);
        return reviews.map(ReadReviewResponse::toDto);
    }

    // 리뷰 조회(가게내 별점 기준)
    @Transactional(readOnly = true)
    public Page<ReadReviewResponse> getReviewsByRating(Long storeId, Integer rating, int page, int size) {

        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;

        // pageable 객체 생성일 기준 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        // Page 조회
        Page<Review> reviews = reviewRepository.findByStoreIdAndRating(storeId, rating, pageable);

        return reviews
                .map(ReadReviewResponse::toDto);
    }

    // 리뷰 수정
    @Transactional
    public UpdateReviewResponse updateReview(Long storeId, Long reviewId, AuthUser user, UpdateReviewRequest request) {
        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId).orElseThrow(ReviewNotFoundException::new);

        if (!user.getId().equals(review.getUser().getId())) {
            throw new IllegalStateException("작성자가 아닙니다.");
        }

        if (request.getRating() != null) {
            review.updateRating(request.getRating());
        }

        if (request.getContent() != null) {
            review.updateContent(request.getContent());
        }

//        reviewRepository.save(review);

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

        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId).orElseThrow(ReviewNotFoundException::new);

        if (!user.getId().equals(review.getUser().getId())) {
            throw new IllegalStateException("작성자가 아닙니다.");
        }

        review.delete();
    }
}

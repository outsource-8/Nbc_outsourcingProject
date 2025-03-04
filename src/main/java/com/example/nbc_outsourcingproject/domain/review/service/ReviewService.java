package com.example.nbc_outsourcingproject.domain.review.service;

import com.example.nbc_outsourcingproject.domain.review.dto.request.CreateReviewRequest;
import com.example.nbc_outsourcingproject.domain.review.dto.response.CreateReviewResponse;
import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import com.example.nbc_outsourcingproject.domain.review.repository.ReviewRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.service.StoreService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final OrderService orderService;

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
}

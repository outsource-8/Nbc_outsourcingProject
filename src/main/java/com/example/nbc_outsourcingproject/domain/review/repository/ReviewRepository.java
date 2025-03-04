package com.example.nbc_outsourcingproject.domain.review.repository;

import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStoreId(Long storeId, Pageable pageable);

    Page<Review> findByStoreIdAndRating(Long storeId, int rating, Pageable pageable);
}

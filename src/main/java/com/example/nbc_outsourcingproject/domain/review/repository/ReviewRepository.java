package com.example.nbc_outsourcingproject.domain.review.repository;

import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

package com.example.nbc_outsourcingproject.domain.review.repository;

import com.example.nbc_outsourcingproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN fetch r.store WHERE r.store.id = :storeId AND r.deleted = false")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN fetch r.store WHERE r.store.id = :storeId AND r.rating = :rating AND r.deleted = false")
    Page<Review> findByStoreIdAndRating(@Param("storeId") Long storeId, @Param("rating") Integer rating, Pageable pageable);

    Optional<Review> findByStoreIdAndId(Long storeId, Long id);
}

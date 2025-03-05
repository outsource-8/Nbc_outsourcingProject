package com.example.nbc_outsourcingproject.domain.store.repository;

import com.example.nbc_outsourcingproject.domain.common.exception.details.InvalidRequestException;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 가게 다건 조회
    @Query("SELECT s FROM Store s JOIN FETCH s.user WHERE s.isShutDown = false " +
            "AND (:name IS NULL OR s.name LIKE CONCAT('%', :name, '%') ) " +
            "ORDER BY s.name")
    Page<Store> findStores(
            @Param("name") String name,
            Pageable pageable
    );

    // 소유한 가게 다건 조회
    @Query("SELECT s From Store s JOIN FETCH s.user WHERE s.user.id = :userId AND s.isShutDown = false ")
    List<Store> findAllByUserId(@Param("userId") Long userId);

    // 아이디로 가게 조회
    @Query("SELECT s FROM Store s WHERE s.id = :storeId AND s.isShutDown= false")
    Optional<Store> findStoreById(@Param("storeId") Long storeId);

    @Query("SELECT s From Store s JOIN FETCH s.user WHERE s.user.id = :userId AND s.isShutDown = false ")
    default Store findStoreBy(Long storeId) {
        return findStoreById(storeId).orElseThrow(() -> new InvalidRequestException("해당 가게가 존재하지 않습니다."));
    }

    @Query("SELECT s.id FROM Store s WHERE s.user.id = :userId")
    List<Long> findStoreByUserId(Long userId);
}

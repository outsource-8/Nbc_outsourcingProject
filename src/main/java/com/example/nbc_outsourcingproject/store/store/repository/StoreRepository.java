package com.example.nbc_outsourcingproject.store.store.repository;

import com.example.nbc_outsourcingproject.store.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 가게 다건 조회
    @Query("SELECT s FROM Store s JOIN FETCH s.fakeUser WHERE " +
            "(:name IS NULL OR s.name LIKE CONCAT('%', :name, '%') ) "+
            "ORDER BY s.name")
    Page<Store> findStores(
            @Param("name") String name,
            Pageable pageable
    );

    // 소유한 가게 다건 조회
    @Query("SELECT s From Store s JOIN FETCH s.fakeUser WHERE s.fakeUser.Id = :userId")
    List<Store> findAllByFakeUserId(@Param("userId") Long userId);
}

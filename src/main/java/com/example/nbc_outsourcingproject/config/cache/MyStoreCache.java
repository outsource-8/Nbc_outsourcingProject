package com.example.nbc_outsourcingproject.config.cache;

import com.example.nbc_outsourcingproject.domain.menu.exception.details.InvalidStoreOwner;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.StoreNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyStoreCache {

    private final StoreRepository storeRepository;

    // user가 소유하고 있는 가게인지 확인하기 위한 메서드
    @Transactional(readOnly = true)
    public void validateStoreOwner(Long userId, Long storeId) {
        // storeId 의 가게가 존재하는지 먼저 확인
        if (! storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException();
        }
        // userId를 이용해 가게 정보 호출
        List<Long> cacheStore = getCacheStore(userId);

        // userId가 소유하고 있는 가게와 요청한 storeId가 맞지 않을 경우 예외 발생
        if (!cacheStore.contains(storeId)) {
            throw new InvalidStoreOwner();
        }
    }

    // userId를 key로 이용해 저장된 myStores 반환
    @Cacheable(key = "#userId", value = "myStores")
    public List<Long> getCacheStore(Long userId) {
        List<Long> stores = storeRepository.findStoreByUserId(userId); // 사용자 ID로 상점 찾기
        if (stores.isEmpty()) {
            throw new StoreNotFoundException(); // 상점이 없을 경우 예외 발생
        }
        return stores;
    }

}

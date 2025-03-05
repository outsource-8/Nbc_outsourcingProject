package com.example.nbc_outsourcingproject.domain.store.service;

import com.example.nbc_outsourcingproject.config.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.exception.MaxStoreCreationException;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class StoreOwnerService {

    private final MyStoreCache myStoreCache;
    private final StoreRepository storeRepository;

    private final Map<Long, List<Long>> storeCache = new ConcurrentHashMap<>();

    // 가게 생성
    @Transactional
    public StoreSaveResponse saveStore(User user, @Valid StoreSaveRequest storeSaveRequest) {

        validateStoreCreationLimit(user);

        Store store = new Store(
                user,
                storeSaveRequest.getName(),
                storeSaveRequest.getAddress(),
                storeSaveRequest.getMinOrderAmount(),
                storeSaveRequest.getStoreInfo(),
                storeSaveRequest.getOpened(),
                storeSaveRequest.getClosed()
        );

        Store savedStore = storeRepository.save(store);
        saveStoreToCache(user.getId(), savedStore.getId()); //생성된 가게 캐시에 저장

        return new StoreSaveResponse(
                savedStore.getName(),
                savedStore.getStoreInfo(),
                savedStore.getMinOrderAmount(),
                savedStore.getClosed(),
                savedStore.getOpened()
        );
    }

    // 소유한 가게 조회
    public List<StoreResponse> getStoresMine(Long userId) {

        List<Store> storesMine = storeRepository.findAllByUserId(userId);

        return storesMine.stream()
                .map(store -> new StoreResponse(
                        store.getName(),
                        store.getStoreInfo(),
                        store.getMinOrderAmount(),
                        store.getClosed(),
                        store.getOpened()))
                .toList();
    }


    // 생성된 가게 캐시 저장
    @Cacheable(key = "#userId", value = "myStores")
    public List<Long> saveStoreToCache(Long userId, Long storeId) {
        List<Long> cacheValue = storeCache.get(userId);
        cacheValue.add(storeId);
        storeCache.put(userId, cacheValue);

        return cacheValue;
    }

    // 캐시 수정 <가게 삭제>
    @CachePut(key = "#userId", value = "myStores")
    public List<Long> removeStoreToCache(Long userId, Long storeId) {
        myStoreCache.validateStoreOwner(userId, storeId);
        List<Long> cacheStore = myStoreCache.getCacheStore(userId);

        // int index로 remove메서드가 실행될 위험이 있어 wrapper 타입을 정확하게 명시
        cacheStore.remove(Long.valueOf(storeId));
        return cacheStore;
    }

    // 가게가 3개 초과시 생성 불가
    private void validateStoreCreationLimit(User user) {
        List<Store> storesMine = storeRepository.findAllByUserId(user.getId());
        if (storesMine.size() > 3) {
            throw new MaxStoreCreationException();
        }
    }
}

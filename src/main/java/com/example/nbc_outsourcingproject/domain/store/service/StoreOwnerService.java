package com.example.nbc_outsourcingproject.domain.store.service;

import com.example.nbc_outsourcingproject.global.exception.store.MaxStoreCreationException;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreUpdateRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreOwnerService {

    private final MyStoreCache myStoreCache;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 가게 생성
    @Transactional
    public StoreSaveResponse saveStore(AuthUser user, @Valid StoreSaveRequest storeSaveRequest) {
        User storeOwner = userRepository.findByIdOrElseThrow(user.getId());
        validateStoreCreationLimit(user);
        Store store = StoreSaveRequest.to(storeOwner, storeSaveRequest);
        storeRepository.save(store);
        saveStoreToCache(user.getId(), store.getId()); //생성된 가게 캐시에 저장
        return StoreSaveResponse.from(store);
    }

    // 소유한 가게 조회
    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresMine(AuthUser authUser) {
        List<Store> storesMine = storeRepository.findAllByUserId(authUser.getId());
        return storesMine.stream()
                .map(StoreResponse::from)
                .toList();
    }

    // 가게 정보 수정
    @Transactional
    public StoreResponse updateStore(AuthUser user, Long storeId, StoreUpdateRequest storeUpdateRequest) {
        myStoreCache.validateStoreOwner(user.getId(), storeId);
        Store store = storeRepository.findStoreBy(storeId);
        store.updateFrom(storeUpdateRequest);
        return StoreResponse.from(store);
    }

    // 가게 폐업 처리
    @Transactional
    public void shutDownStore(AuthUser user, Long storeId) {
        Store store = storeRepository.findStoreBy(storeId);
        myStoreCache.validateStoreOwner(user.getId(), storeId);
        removeStoreToCache(user.getId(), storeId);
        store.shutDown();
    }

    // 생성된 가게 캐시 저장
    @CachePut(key = "#userId", value = "myStores")
    public List<Long> saveStoreToCache(Long userId, Long storeId) {
        List<Long> cacheValue = new ArrayList<>();
        cacheValue.add(storeId);
        return cacheValue;
    }

    // 캐시 수정 <가게 삭제>
    @CachePut(key = "#userId", value = "myStores")
    public List<Long> removeStoreToCache(Long userId, Long storeId) {
        List<Long> cacheValue = myStoreCache.getCacheStore(userId);

        // int index로 remove메서드가 실행될 위험이 있어 wrapper 타입을 정확하게 명시
        cacheValue.remove(Long.valueOf(storeId));
        return cacheValue;
    }

    // 가게가 3개 초과시 생성 불가
    private void validateStoreCreationLimit(AuthUser user) {
        List<Store> storesMine = storeRepository.findAllByUserId(user.getId());
        if (storesMine.size() >= 3) {
            throw new MaxStoreCreationException();
        }
    }

}

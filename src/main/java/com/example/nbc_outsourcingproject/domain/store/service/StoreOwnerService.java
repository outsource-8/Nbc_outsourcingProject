package com.example.nbc_outsourcingproject.domain.store.service;

import com.example.nbc_outsourcingproject.domain.common.exception.InvalidRequestException;
import com.example.nbc_outsourcingproject.domain.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreOwnerService {

    private final StoreRepository storeRepository;

    // 가게 생성
    @Transactional
    public StoreSaveResponse saveStore(User user, @Valid StoreSaveRequest storeSaveRequest) {

        validateOwner(user);
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

    // OWNER 사용자 검증
    private void validateOwner(User user) {
        if (!user.isOwner()) {
            throw new UnauthorizedException("Owner가 아닙니다.");
        }
    }

    // 가게가 3개 초과시 생성 불가
    public void validateStoreCreationLimit(User user){
        List<Store> storesMine = storeRepository.findAllByUserId(user.getId());
        if (storesMine.size() > 3){
            throw new InvalidRequestException("가게는 최대 3개까지 생성할 수 있습니다.");
        }
    }
}

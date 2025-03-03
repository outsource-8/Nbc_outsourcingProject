package com.example.nbc_outsourcingproject.store.store.service;

import com.example.nbc_outsourcingproject.store.common.exception.InvalidRequestException;
import com.example.nbc_outsourcingproject.store.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.entity.Store;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreOwnerService {

    private final StoreRepository storeRepository;

    // 가게 생성
    @Transactional
    public StoreSaveResponse saveStore(FakeUser fakeUser, @Valid StoreSaveRequest storeSaveRequest) {

        validateOwner(fakeUser);
        validateStoreCreationLimit(fakeUser);

        Store store = new Store(
                fakeUser,
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
    public List<StoreResponse> getStoresMine(Long fakeUserId) {

        List<Store> storesMine = storeRepository.findAllByFakeUserId(fakeUserId);

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
    private void validateOwner(FakeUser fakeUser) {
        if (!fakeUser.isOwner()) {
            throw new UnauthorizedException("Owner가 아닙니다.");
        }
    }

    // 가게가 3개 초과시 생성 불가
    public void validateStoreCreationLimit(FakeUser fakeUser){
        List<Store> storesMine = storeRepository.findAllByFakeUserId(fakeUser.getId());
        if (storesMine.size() > 3){
            throw new InvalidRequestException("가게는 최대 3개까지 생성할 수 있습니다.");
        }
    }
}

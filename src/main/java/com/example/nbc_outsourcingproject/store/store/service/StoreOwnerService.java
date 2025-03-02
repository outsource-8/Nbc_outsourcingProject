package com.example.nbc_outsourcingproject.store.store.service;

import com.example.nbc_outsourcingproject.store.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.entity.Store;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.repository.StoreRepository;
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
    public StoreSaveResponse saveStore(FakeUser fakeUser, @Valid StoreSaveRequest storeSaveRequest) {

        validateOwner(fakeUser);

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
        // validateOwner(fakeUser);
        // 사용자 아이디 가져오기
        // Long fakeUserId = fakeUser.getId();

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
}

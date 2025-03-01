package com.example.nbc_outsourcingproject.store.store.service;

import com.example.nbc_outsourcingproject.store.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.entity.Store;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // OWNER 사용자 검증
    private void validateOwner(FakeUser fakeUser) {
        if (!fakeUser.isOwner()) {
            throw new UnauthorizedException("Owner만 가게를 생성할 수 있습니다.");
        }
    }
}

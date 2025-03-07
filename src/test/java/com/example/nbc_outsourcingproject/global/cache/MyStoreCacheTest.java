package com.example.nbc_outsourcingproject.global.cache;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = MyStoreCacheTest.class)
class MyStoreCacheTest {

    @InjectMocks
    MyStoreCache myStoreCache;

    @InjectMocks
    StoreOwnerService storeOwnerService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    UserRepository userRepository;


    @Test
    void 생성한_store_캐시에_저장() {

        Long userId = 1L;
        Long storeId = 1L;

        Store store = new Store();

        ReflectionTestUtils.setField(store, "id", storeId);

        given(storeRepository.save(any(Store.class))).willReturn(store);
        given(storeRepository.findStoreByUserId(userId)).willReturn(List.of(storeId));

        assertEquals(myStoreCache.getCacheStore(userId).get(0), store.getId());
    }

    @Test
    void 캐시에_여러_store_저장() {

        Long userId = 1L;
        Long store1Id = 1L;
        Long store2Id = 2L;

        Store store1 = new Store();
        Store store2 = new Store();

        ReflectionTestUtils.setField(store1, "id", store1Id);
        ReflectionTestUtils.setField(store2, "id", store2Id);

        given(storeRepository.save(any(Store.class))).willReturn(store1);
        given(storeRepository.save(any(Store.class))).willReturn(store2);
        given(storeRepository.findStoreByUserId(userId)).willReturn(List.of(store1Id, store2Id));

        assertEquals(myStoreCache.getCacheStore(userId).get(0), store1.getId());
        assertEquals(myStoreCache.getCacheStore(userId).get(1), store2.getId());
    }

    @Test
    void 캐시에_있는_store_조회() {

        Long userId = 1L;
        Long store1Id = 1L;
        Long store2Id = 2L;

        given(storeRepository.findStoreByUserId(userId)).willReturn(List.of(store1Id, store2Id));

        assertEquals(myStoreCache.getCacheStore(userId), List.of(store1Id, store2Id));
    }

    @Test
    void 가게_생성_시_캐시_저장(){
        Store store = new Store();
        AuthUser authUser = new AuthUser(1L, "email@email.com", UserRole.OWNER);
        StoreSaveRequest storeSaveRequest = new StoreSaveRequest("가게", "주소", 12000, "소개", LocalTime.of(12,30), LocalTime.of(21,00));

        ReflectionTestUtils.setField(store, "id", 2L);
        given(storeRepository.findStoreById(2L)).willReturn(Optional.of(store));
        given(storeRepository.findStoreByUserId(authUser.getId())).willReturn(List.of(2L));

        storeOwnerService.saveStore(authUser, storeSaveRequest);
        List<Long> cacheStore = myStoreCache.getCacheStore(authUser.getId());

        assertEquals(cacheStore.get(0), store.getId());
    }

}
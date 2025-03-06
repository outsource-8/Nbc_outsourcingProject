package com.example.nbc_outsourcingproject.global.cache;

import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = MyStoreCacheTest.class)
class MyStoreCacheTest {

    @InjectMocks
    MyStoreCache myStoreCache;

    @Mock
    StoreRepository storeRepository;


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
}
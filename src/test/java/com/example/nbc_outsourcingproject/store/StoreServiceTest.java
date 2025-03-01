package com.example.nbc_outsourcingproject.store;

import com.example.nbc_outsourcingproject.store.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.entity.Store;
import com.example.nbc_outsourcingproject.store.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.store.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void 가게를_다_건_조회한다() {
        //given
        FakeUser fakeUser = new FakeUser(1L, "사용자", "OWNER");
        String storeName = "1";
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);

        List<Store> storeList = List.of(
                new Store(fakeUser, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(fakeUser, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(fakeUser, "test2", "address2", 2000, "storeInfo2", LocalTime.of(18, 30, 45), LocalTime.of(14, 30, 45))
        );

        List<Store> filteredList = storeList.stream().filter(store -> store.getName().contains(storeName)).toList();
        Page<Store> storesPage = new PageImpl<>(filteredList, pageable, storeList.size());
        given(storeRepository.findStores(storeName, pageable)).willReturn(storesPage);

        //when
        Page<StoreResponse> response = storeService.getStores(storeName, page, size);

        //then
        assertNotNull(response);
        assertEquals(filteredList.size(),response.getContent().size());

        for (StoreResponse storeResponse : response.getContent()) {
            assertTrue(storeResponse.getName().contains(storeName));  // storeName이 포함된 가게만 반환 되었는지 확인
        }

    }

}

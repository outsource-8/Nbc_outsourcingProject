package com.example.nbc_outsourcingproject.store;

import com.example.nbc_outsourcingproject.store.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.store.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.store.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.store.store.entity.FakeUser;
import com.example.nbc_outsourcingproject.store.store.entity.Store;
import com.example.nbc_outsourcingproject.store.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.store.store.service.StoreOwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class StoreOwnerServiceTest {

    @InjectMocks
    private StoreOwnerService storeOwnerService;
    @Mock
    private StoreRepository storeRepository;

    @Test
    public void 가게를_정상적으로_등록한다() {
        //given
        StoreSaveRequest request = new StoreSaveRequest(
                "name",
                "address",
                1000,
                "storeInfo",
                LocalTime.of(14, 30, 45, 123000000),
                LocalTime.of(14, 30, 45, 123000000)
        );
        FakeUser fakeUser = new FakeUser(1L, "사용자", "OWNER");
        Store store = new Store(
                fakeUser,
                request.getName(),
                request.getAddress(),
                request.getMinOrderAmount(),
                request.getStoreInfo(),
                request.getOpened(),
                request.getClosed()
        );
        given(storeRepository.save(any())).willReturn(store);

        //when
        StoreSaveResponse response = storeOwnerService.saveStore(fakeUser, request);

        //then
        assertNotNull(response);
    }

    @Test
    public void 가게_생성_시_OWNER가_아니면_UnauthorizedException_에러를_던진다() {
        //given
        StoreSaveRequest request = new StoreSaveRequest(
                "name",
                "address",
                1000,
                "storeInfo",
                LocalTime.of(14, 30, 45, 123000000),
                LocalTime.of(14, 30, 45, 123000000)
        );
        FakeUser fakeUser = new FakeUser(1L, "사용자", "USER");

        //when
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> storeOwnerService.saveStore(fakeUser, request));

        //then
        assertEquals("Owner만 가게를 생성할 수 있습니다.", exception.getMessage());
    }

}

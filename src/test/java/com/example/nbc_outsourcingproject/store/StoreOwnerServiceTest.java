package com.example.nbc_outsourcingproject.store;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.common.exception.UnauthorizedException;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        User user = new User(1L, "사용자", UserRole.OWNER);
        Store store = new Store(
                user,
                request.getName(),
                request.getAddress(),
                request.getMinOrderAmount(),
                request.getStoreInfo(),
                request.getOpened(),
                request.getClosed()
        );
        given(storeRepository.save(any())).willReturn(store);

        //when
        StoreSaveResponse response = storeOwnerService.saveStore(user, request);

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
        User user = new User(1L, "사용자", UserRole.USER);

        //when
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> storeOwnerService.saveStore(user, request));

        //then
        assertEquals("Owner만 가게를 생성할 수 있습니다.", exception.getMessage());
    }

    @Test
    public void OWNER가_소유한_가게를_조회한다() {
        //given
        User user = new User(1L, "사용자1", UserRole.OWNER);

        List<Store> storeList = List.of(
                new Store(user, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(user, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45))
        );

        given(storeRepository.findAllByUserId(user.getId())).willReturn(storeList);

        //when
        List<StoreResponse> response = storeOwnerService.getStoresMine(user.getId());

        //then
        assertNotNull(response);
        assertEquals(2, response.size());

    }
}

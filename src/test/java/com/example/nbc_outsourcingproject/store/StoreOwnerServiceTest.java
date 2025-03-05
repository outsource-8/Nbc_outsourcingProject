package com.example.nbc_outsourcingproject.store;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import com.example.nbc_outsourcingproject.domain.common.dto.AuthUser;
import com.example.nbc_outsourcingproject.domain.common.exception.details.UnauthorizedException;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.example.nbc_outsourcingproject.domain.auth.enums.UserRole.OWNER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class StoreOwnerServiceTest {

    @InjectMocks
    private StoreOwnerService storeOwnerService;
    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

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
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User user = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));

        Store store = new Store(
                user,
                request.getName(),
                request.getAddress(),
                request.getMinOrderAmount(),
                request.getStoreInfo(),
                request.getOpened(),
                request.getClosed()
        );
        given(storeRepository.save(any(Store.class))).willReturn(store);

        //when
        StoreSaveResponse response = storeOwnerService.saveStore(authUser, request);

        //then
        assertNotNull(response);
    }

    @Test
    public void 가게_생성_시_OWNER가_아니면_UnauthorizedException_에러를_던진다() {
        // given
        StoreSaveRequest request = new StoreSaveRequest(
                "name",
                "address",
                1000,
                "storeInfo",
                LocalTime.of(14, 30, 45, 123000000),
                LocalTime.of(14, 30, 45, 123000000)
        );
        // OWNER가 아닌 사용자로 설정 (UserRole.USER)
        AuthUser nonOwner = new AuthUser(1L, "email@email.com", UserRole.USER);
        User user = new User(nonOwner.getId(), nonOwner.getEmail(), nonOwner.getUserRole());
        given(userRepository.findById(nonOwner.getId())).willReturn(Optional.of(user));

        // when
        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> storeOwnerService.saveStore(nonOwner, request));

        // then
        assertEquals("Owner가 아닙니다.", exception.getMessage());
    }

    @Test
    public void OWNER가_소유한_가게를_조회한다() {
        //given
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User owner = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());


        List<Store> storeList = List.of(
                new Store(owner, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(owner, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45))
        );

        given(storeRepository.findAllByUserId(owner.getId())).willReturn(storeList);

        //when
        List<StoreResponse> response = storeOwnerService.getStoresMine(authUser);

        //then
        assertNotNull(response);
        assertEquals(2, response.size());

    }
}

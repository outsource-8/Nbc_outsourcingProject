package com.example.nbc_outsourcingproject.domain.store;

import com.example.nbc_outsourcingproject.domain.auth.AuthUser;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreUpdateRequest;
import com.example.nbc_outsourcingproject.domain.store.service.StoreOwnerService;
import com.example.nbc_outsourcingproject.global.cache.MyStoreCache;
import com.example.nbc_outsourcingproject.global.exception.store.MaxStoreCreationException;
import com.example.nbc_outsourcingproject.domain.store.dto.request.StoreSaveRequest;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreResponse;
import com.example.nbc_outsourcingproject.domain.store.dto.response.StoreSaveResponse;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private MyStoreCache myStoreCache;

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
                LocalTime.of(14, 30),
                LocalTime.of(14, 30)
        );
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User user = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
        Store store = StoreSaveRequest.to(user, request);
        given(storeRepository.save(any(Store.class))).willReturn(store);

        //when
        StoreSaveResponse response = storeOwnerService.saveStore(authUser, request);

        //then
        assertNotNull(response);
    }

    @Test
    public void 가게를_초과_생성시_MaxStoreCreationException가_발생한다() {
        //given
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User owner = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
        List<Store> storeList = List.of(
                new Store(owner, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(owner, "test1", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45)),
                new Store(owner, "test2", "address2", 2000, "storeInfo2", LocalTime.of(15, 30, 45), LocalTime.of(14, 30, 45))
        );
        given(storeRepository.findAllByUserId(authUser.getId())).willReturn(storeList);
        given(userRepository.findByIdOrElseThrow(authUser.getId())).willReturn(owner);

        StoreSaveRequest request = new StoreSaveRequest(
                "name",
                "address",
                1000,
                "storeInfo",
                LocalTime.of(14, 30),
                LocalTime.of(14, 30)
        );

        //when&then
        assertThrows(MaxStoreCreationException.class, () -> {
            storeOwnerService.saveStore(authUser, request);
        });

    }

    @Test
    public void 가게_정보를_수정한다() {
        //given
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User owner = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
        Store store = new Store(owner, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30), LocalTime.of(14, 30));
        given(storeRepository.findStoreBy(store.getId())).willReturn(store);

        StoreUpdateRequest request = new StoreUpdateRequest(
                "수정된 name",
                "수정된 address",
                10000,
                "수정된 storeInfo",
                LocalTime.of(18, 30),
                LocalTime.of(22, 30)
        );

        //when
        StoreResponse response = storeOwnerService.updateStore(authUser, store.getId(), request);

        //then
        assertNotNull(response);
        assertEquals("수정된 name", response.getName());

    }

    @Test
    public void 가게를_폐업_처리한다() {
        //given
        AuthUser authUser = new AuthUser(1L, "email@email.com", OWNER);
        User owner = new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
        Store store = new Store(owner, "name1", "address1", 1000, "storeInfo1", LocalTime.of(14, 30), LocalTime.of(14, 30));

        given(storeRepository.findStoreBy(1L)).willReturn(store);

        List<Long> cachedStores = new ArrayList<>();
        cachedStores.add(1L);
        given(myStoreCache.getCacheStore(authUser.getId())).willReturn(cachedStores);

        //when
        storeOwnerService.shutDownStore(authUser, 1L);

        //then
        assertTrue(store.getIsShutDown());
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

        given(storeRepository.findAllByUserId(authUser.getId())).willReturn(storeList);

        //when
        List<StoreResponse> response = storeOwnerService.getStoresMine(authUser);

        //then
        assertNotNull(response);
        assertEquals(2, response.size());

    }

    //    @Test
//    public void 가게_생성_시_OWNER가_아니면_UnauthorizedException_에러를_던진다() {
//        // given
//        StoreSaveRequest request = new StoreSaveRequest(
//                "name",
//                "address",
//                1000,
//                "storeInfo",
//                LocalTime.of(14, 30, 45, 123000000),
//                LocalTime.of(14, 30, 45, 123000000)
//        );
//        // OWNER가 아닌 사용자로 설정 (UserRole.USER)
//        AuthUser nonOwner = new AuthUser(1L, "email@email.com", UserRole.USER);
//        User user = new User(nonOwner.getId(), nonOwner.getEmail(), nonOwner.getUserRole());
//        given(userRepository.findById(nonOwner.getId())).willReturn(Optional.of(user));
//
//        // when
//        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
//                () -> storeOwnerService.saveStore(nonOwner, request));
//
//        // then
//        assertEquals("Owner가 아닙니다.", exception.getMessage());
//    }
}

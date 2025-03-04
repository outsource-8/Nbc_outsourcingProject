package com.example.nbc_outsourcingproject.domain.common.util;


import com.example.nbc_outsourcingproject.domain.menu.exception.details.InvalidStoreOwner;
import com.example.nbc_outsourcingproject.domain.menu.exception.details.StoreNotFoundException;
import com.example.nbc_outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class StoreOwnerValidator {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;


    @Transactional(readOnly = true)
    public void validateStoreOwner(Long storeId, Long userId) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException();
        }

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        User user = userRepository.findById(userId).orElseThrow();

        if (!storeRepository.existsByIdAndUser(store, user)) {
            throw new InvalidStoreOwner();
        }
    }

}

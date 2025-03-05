package com.example.nbc_outsourcingproject.domain.menu.repository;

import com.example.nbc_outsourcingproject.domain.menu.entity.Category;
import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import com.example.nbc_outsourcingproject.domain.store.entity.Store;
import com.example.nbc_outsourcingproject.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Transactional
class MenuRepositoryTest {

    @InjectMocks
    private StoreRepository storeRepository;

    @InjectMocks
    private MenuRepository menuRepository;


    @Test
    public void menu로_store_받아오기() {
        // Given: Store와 Menu 저장
        Store store = new Store();
        storeRepository.save(store);

        Menu menu = new Menu(store, Category.MAIN, "1", 12000, null);
        menuRepository.save(menu);

        // When: menuId를 이용해 storeId 조회
        Long storeId = menuRepository.findByMenuIdForStoreId(menu.getId());

        // Then: 조회된 storeId가 기대한 값과 일치하는지 확인
        assertNotNull(storeId);  // Null이 아님을 확인
        assertEquals(store.getId(), storeId);  // store의 ID와 동일한지 확인
    }
}
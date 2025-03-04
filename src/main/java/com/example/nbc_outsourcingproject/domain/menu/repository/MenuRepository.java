package com.example.nbc_outsourcingproject.domain.menu.repository;

import com.example.nbc_outsourcingproject.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {


    @Query("SELECT m FROM Menu m JOIN FETCH m.store WHERE m.store.id = :storeId")
    List<Menu> findByStoreId(Long storeId);

    @Query("SELECT m.store.id FROM Menu m WHERE m.id = :menuId")
    Long findByMenuIdForStoreId(Long menuId);


}

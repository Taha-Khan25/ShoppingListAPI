package com.example.Task.Repositories;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,Integer> {

    List<ShoppingList> findByusername(String name);


    @Transactional
    @Modifying
    @Query("UPDATE ShoppingList i SET i.quantity = :quantity WHERE i.itemId = :item_id")
    void updateQunatity(Integer item_id,Integer quantity);
}

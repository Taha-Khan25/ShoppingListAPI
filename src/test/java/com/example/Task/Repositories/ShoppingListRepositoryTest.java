package com.example.Task.Repositories;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import com.example.Task.Models.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShoppingListRepositoryTest {


    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void SaveListItemTest() {

        ShoppingList shoppingList=ShoppingList.builder()
                .itemName("Mango")
                .quantity(10)
                .username("test")
                .build();
        shoppingListRepository.save(shoppingList);

        Assertions.assertThat(shoppingList.getItemId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getAllItemsTest()
    {
        List<ShoppingList> list=shoppingListRepository.findAll();

        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void getItem_byId()
    {
        Optional<ShoppingList> item=shoppingListRepository.findById(1);
        Assertions.assertThat(item.get().getItemId()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void getItem_byUsername()
    {
        List<ShoppingList> items=shoppingListRepository.findByusername("test");
        Assertions.assertThat(items.size()).isGreaterThan(0);
    }


    @Test
    @Order(5)
    @Rollback(value = false)
    public void UpdateItem_byId()
    {
        ShoppingList list=shoppingListRepository.findById(1).get();

        list.setQuantity(20);

        ShoppingList UpdatedList=shoppingListRepository.save(list);

        Assertions.assertThat(UpdatedList.getQuantity()).isEqualTo(20);
    }

    @Test
    @Order(6)
    public void DeleteUser_byId()
    {
        ShoppingList list=shoppingListRepository.findById(1).get();

        shoppingListRepository.delete(list);

        ShoppingList list1=null;

        Optional<ShoppingList> optionalShoppingList=shoppingListRepository.findById(1);

        if (optionalShoppingList.isPresent())
        {
            list1=optionalShoppingList.get();
        }

        Assertions.assertThat(list1).isNull();
    }

}
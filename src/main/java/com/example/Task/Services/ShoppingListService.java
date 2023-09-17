package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import com.example.Task.Repositories.ShoppingListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ShoppingListService {
    @Autowired
    ShoppingListRepository shoppingListRepository;

    public void create(ShoppingList shoppingList)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        String name=securedUser.getUsername();
        shoppingList.setMy_user(securedUser.getUsers());
        shoppingList.setUsername(name);
        shoppingListRepository.save(shoppingList);
    }

    public void delete(Integer item_id)
    {
        shoppingListRepository.deleteById(item_id);
    }

    public List<ShoppingList> findByUsername(String username) {
        return shoppingListRepository.findByusername(username);
    }

    public List<ShoppingList> findAll() {
        return shoppingListRepository.findAll();
    }

    public void updateQuantity(Integer item_id,Integer quantity)
    {
          shoppingListRepository.updateQunatity(item_id,quantity);
    }
}

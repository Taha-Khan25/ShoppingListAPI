package com.example.Task.Controller;

import com.example.Task.Dto.CreateListRequest;
import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import com.example.Task.Services.ShoppingListService;
import com.example.Task.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class ShoppingListController {

    @Autowired
    ShoppingListService shoppingListService;

    @PostMapping("/list/add")
    public String createList(@RequestBody @Valid CreateListRequest createListRequest) throws Exception {

        boolean isAllowed = AuthenticationCheck(Constants.ADD_ITEM_AUTHORITY);
        if (isAllowed) {
            shoppingListService.create(createListRequest.listBuilder());
            return "Added Successfully";
        }
        return "Failed: User Not Authenticated";
    }


    @DeleteMapping("/list/delete/{item_id}")
    public String deleteItem(@PathVariable Integer item_id) throws Exception {
        boolean isAllowed = AuthenticationCheck(Constants.DELETE_ITEM_AUTHORITY);
        if (isAllowed) {
            shoppingListService.delete(item_id);
            return "Deleted Successfully";
        }
        return "Failed: User Not Authenticated";
    }

    @GetMapping("/my-list")
    public List<ShoppingList> findByUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        String username=user.getUsername();
        return shoppingListService.findByUsername(username);
    }


    @PutMapping("/update/{id}/{quantity}")
    public void updateQuantity(@PathVariable Integer id,@PathVariable Integer quantity)
    {
          shoppingListService.updateQuantity(id,quantity);
    }


    public boolean AuthenticationCheck(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

        for (GrantedAuthority grantedAuthority : securedUser.getAuthorities()) {
            String[] authorities = grantedAuthority.getAuthority().split(Constants.DELIMITER);
            boolean isAllowed = Arrays.stream(authorities).anyMatch(x -> authority.equals(x));
            if (isAllowed) {
                return true;
            }
        }
        return false;
    }

}


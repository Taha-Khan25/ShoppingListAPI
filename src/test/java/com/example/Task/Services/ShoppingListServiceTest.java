package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import com.example.Task.Repositories.ShoppingListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        shoppingListService = new ShoppingListService(shoppingListRepository);
    }

    @Test
    void create() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecuredUser securedUser = mock(SecuredUser.class);
        when(authentication.getPrincipal()).thenReturn(securedUser);
        when(securedUser.getUsername()).thenReturn("testUser");

        ShoppingList list = new ShoppingList(1, "Mango", 10);

        shoppingListService.create(list);

        ArgumentCaptor<ShoppingList> argumentCaptor =
                ArgumentCaptor.forClass(ShoppingList.class);

        verify(shoppingListRepository)
                .save(argumentCaptor.capture());

        ShoppingList value = argumentCaptor.getValue();

        Assertions.assertThat(value).isEqualTo(list);
    }

    @Test
    void delete() {
        Integer id = 1;
        shoppingListService.delete(id);
        verify(shoppingListRepository).deleteById(id);
    }

    @Test
    void findByUsername() {
        String username = "test";
        shoppingListService.findByUsername(username);
        verify(shoppingListRepository).findByusername(username);
    }

    @Test
    void findAll() {
        shoppingListService.findAll();
        verify(shoppingListRepository).findAll();
    }
}
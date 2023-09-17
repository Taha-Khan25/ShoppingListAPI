package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import com.example.Task.Repositories.UserRepository;
import com.example.Task.Utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    private CreateUserService createuserService;


    @BeforeEach
    void setUp() {
        createuserService  = new CreateUserService(userRepository,userService);
    }

    @Test
    void createOrUpdateUser() {

        Users user = new Users();
        SecuredUser securedUser = new SecuredUser();
        when(user.getSecuredUser()).thenReturn(securedUser);

        SecuredUser savedSecuredUser = new SecuredUser();
        when(userService.save(securedUser, Constants.USER)).thenReturn(savedSecuredUser);

        createuserService.CreateOrUpdateUser(user);
        verify(userService).save(securedUser, Constants.USER);
        verify(user).setSecuredUser(savedSecuredUser);
        verify(userRepository).save(user);
    }

    @Test
    @Disabled
    void getUser() {
    }
}
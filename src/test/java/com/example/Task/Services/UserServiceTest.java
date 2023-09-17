package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import com.example.Task.Repositories.SecuredUserRepository;
import com.example.Task.Repositories.UserRepository;
import com.example.Task.Utils.Constants;
import com.example.Task.Utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SecuredUserRepository securedUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Utils utils;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService=new UserService(securedUserRepository,userRepository,passwordEncoder,utils);
    }

    @Test
    void loadUserByUsername() {
        String username = "testUser";
        SecuredUser securedUser = new SecuredUser();
        securedUser.setUsername(username);
        when(securedUserRepository.findByusername(username)).thenReturn(securedUser);
        UserDetails userDetails = userService.loadUserByUsername(username);
        verify(securedUserRepository).findByusername(username);

        assertEquals(username, userDetails.getUsername());

    }

    @Test
    void save() {

        SecuredUser securedUser = new SecuredUser();
        securedUser.setPassword("plainTextPassword"); //

        when(passwordEncoder.encode("plainTextPasswor")).thenReturn("encryptedPassword");

        when(utils.getAuthoritiesForUser()).thenReturn(Collections.singletonMap("userType", "ROLE_USER"));
        when(securedUserRepository.save(securedUser)).thenReturn(securedUser);

        SecuredUser savedUser = userService.save(securedUser, "userType");
        verify(passwordEncoder).encode("plainTextPassword");
        verify(utils).getAuthoritiesForUser();
        verify(securedUserRepository).save(securedUser);
        assertEquals("encryptedPassword", savedUser.getPassword());
        assertEquals("ROLE_USER", savedUser.getAuthorities());

    }

    @Test
    void createOrUpdateUser() {
        Users user = new Users();
        SecuredUser securedUser = new SecuredUser();
        when(user.getSecuredUser()).thenReturn(securedUser);

        SecuredUser savedSecuredUser = new SecuredUser();
        when(userService.save(securedUser, Constants.USER)).thenReturn(savedSecuredUser);

        userService.CreateOrUpdateUser(user);
        verify(userService).save(securedUser, Constants.USER);
        verify(user).setSecuredUser(savedSecuredUser);
        verify(userRepository).save(user);
    }

    @Test
    @Disabled
    void getUserById() {

    }
}
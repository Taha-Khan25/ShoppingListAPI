package com.example.Task.Controller;


import com.example.Task.Dto.CreateUserRequest;
import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import com.example.Task.Services.CreateUserService;
import com.example.Task.Services.UserService;
import com.example.Task.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    CreateUserService createUserService;

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody @Valid CreateUserRequest createUserRequest)
    {
        userService.CreateOrUpdateUser(createUserRequest.buildUser());
    }

    @GetMapping("/user/id/{id}")
    public Optional<Users> findUserById(@PathVariable("id") Integer userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

        for(GrantedAuthority grantedAuthority: securedUser.getAuthorities()){
            String[] authorities = grantedAuthority.getAuthority().split(Constants.DELIMITER);
            boolean isAllowed = Arrays.stream(authorities).anyMatch(x -> Constants.USER_INFO_AUTHORITY.equals(x));

            if (isAllowed) {
                return userService.getUserById(userId);
            }
        }
        throw new Exception("User is not authorized to do this");
    }

    @GetMapping("/user/{id}")
    public Optional<Users> getUserById(@PathVariable Integer id)
    {
        UserDetails user=  userService.loadUserByUsername("taha");
        System.out.println(user.getPassword());
        return userService.getUserById(id);
    }


    @GetMapping("/find")
    public Optional<Users> findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        Integer studentId = user.getUsers().getId();
        return userService.getUserById(studentId);
    }

}

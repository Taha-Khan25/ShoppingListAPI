package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import com.example.Task.Repositories.UserRepository;
import com.example.Task.Utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class CreateUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    public void CreateOrUpdateUser(Users user)
    {
        SecuredUser securedUser = user.getSecuredUser();
        securedUser = userService.save(securedUser, Constants.USER);
        user.setSecuredUser(securedUser);
        userRepository.save(user);
    }

    public Optional<Users> getUser(Integer id) {
        return userRepository.findById(id);
    }

}

package com.example.Task.Services;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import com.example.Task.Repositories.SecuredUserRepository;
import com.example.Task.Repositories.UserRepository;
import com.example.Task.Utils.Constants;
import com.example.Task.Utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    SecuredUserRepository securedUserRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    Utils utils;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails= securedUserRepository.findByusername(username);
        return userDetails;
    }

    public SecuredUser save(SecuredUser securedUser, String userType) {

        String encryptedPwd = encoder.encode(securedUser.getPassword());
        String authorities = utils.getAuthoritiesForUser().get(userType);
        securedUser.setAuthorities(authorities);
        securedUser.setPassword(encryptedPwd);
        return this.securedUserRepository.save(securedUser);
    }

    public void CreateOrUpdateUser(Users user)
    {
        SecuredUser securedUser = user.getSecuredUser();
        securedUser = save(securedUser, Constants.USER);
        user.setSecuredUser(securedUser);
        userRepository.save(user);
    }


    public Optional<Users> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}
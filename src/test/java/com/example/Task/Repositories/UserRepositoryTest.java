package com.example.Task.Repositories;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecuredUserRepository securedUserRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void SaveUser_Test()
    {
        SecuredUser securedUser =  SecuredUser.builder()
                .username("test")
                .password("test@12345")
                .build();

        securedUserRepository.save(securedUser);

        Users users=Users.builder()
                .name("test_user")
                .email("test@gmail.com")
                .username("test")
                .age(20)
                .phone("987654321")
                .securedUser(securedUser)
                .build();
        userRepository.save(users);
        System.out.println(users.getId());

        Assertions.assertThat(users.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getAllUserTest()
    {
        List<Users> users=userRepository.findAll();

        Assertions.assertThat(users.size()).isGreaterThan(0);


    }


    @Test
    @Order(3)
    public void getUser_byId()
    {
        Optional<Users> users=userRepository.findById(1);

        Assertions.assertThat(users.get().getId()).isEqualTo(1);


    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void UpdateUser_byId()
    {
        Users users=userRepository.findById(1).get();

        users.setEmail("test123@gmail.com");

        Users usersUpdated=userRepository.save(users);

        Assertions.assertThat(usersUpdated.getEmail()).isEqualTo("test123@gmail.com");
    }

    @Test
    @Order(5)
    public void DeleteUser_byId()
    {
        Users users=userRepository.findById(1).get();

        userRepository.delete(users);

        Users users1=null;

        Optional<Users> optionalUsers=userRepository.findByEmail("test123@gmail.com");

        if (optionalUsers.isPresent())
        {
            users1=optionalUsers.get();
        }
        Assertions.assertThat(users1).isNull();
    }

}
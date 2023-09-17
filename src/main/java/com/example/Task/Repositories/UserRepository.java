package com.example.Task.Repositories;

import com.example.Task.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {

  Optional<Users> findByEmail(String email);

}

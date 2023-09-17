package com.example.Task.Repositories;

import com.example.Task.Models.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecuredUserRepository extends JpaRepository<SecuredUser, Integer> {

    SecuredUser findByusername(String name);
}
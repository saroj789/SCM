package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    

}
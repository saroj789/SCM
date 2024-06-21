package com.scm.services;

import com.scm.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String id);

    boolean isUSerExistByEmail(String email);
}

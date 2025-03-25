package org.aumni.dao;

import org.aumni.core.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);
    User create(User user);
    List<User> getAllUsers();
}

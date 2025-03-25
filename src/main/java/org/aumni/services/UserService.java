package org.aumni.services;

import org.aumni.core.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
     Optional<User> findByEmail(String email);
     User create(User user);
     List<User> getAllUsers();
}

package org.aumni.services;

import com.google.inject.Inject;
import org.aumni.core.User;
import org.aumni.dao.UserDao;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{

    UserDao userDao;
    @Inject
    public UserServiceImpl(UserDao userDao){
        this.userDao=userDao;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}

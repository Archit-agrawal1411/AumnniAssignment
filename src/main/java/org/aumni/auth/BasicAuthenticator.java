package org.aumni.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.aumni.core.User;
import org.aumni.dao.UserDao;

import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDao userDAO;

    public BasicAuthenticator(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Optional<User> user = userDAO.findByEmail(credentials.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(credentials.getPassword())) {
            return user;
        }

        return Optional.empty();
    }
}
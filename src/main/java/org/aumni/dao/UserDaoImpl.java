package org.aumni.dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.aumni.core.User;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Inject
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Inside UserDao findByEmail method");
        return Optional.ofNullable(
                uniqueResult(
                        namedTypedQuery("org.aumni.core.User.findByEmail")
                                .setParameter("email", email)
                )
        );
    }

    @Override
    public User create(User user) {
        logger.info("Inside UserDao create method");
        return persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return list(
                namedTypedQuery("org.aumni.core.User.getAllUsers")
        );
    }
}

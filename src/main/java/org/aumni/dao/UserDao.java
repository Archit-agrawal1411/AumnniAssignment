package org.aumni.dao;
import org.aumni.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.util.Optional;


public class UserDao extends AbstractDAO<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(
                uniqueResult(
                        namedTypedQuery("org.aumni.core.User.findByEmail")
                                .setParameter("email", email)
                )
        );
    }

    public User create(User user) {
        return persist(user);
    }
}
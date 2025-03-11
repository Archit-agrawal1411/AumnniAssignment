package org.aumni.dao;
import org.aumni.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.util.List;
import java.util.Optional;


public class UserDao extends AbstractDAO<User> {

    private static final Logger logger= LoggerFactory.getLogger(UserDao.class);
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Optional<User> findByEmail(String email) {
        logger.info("Inside UserDao findByEmail method");
        return Optional.ofNullable(
                uniqueResult(
                        namedTypedQuery("org.aumni.core.User.findByEmail")
                                .setParameter("email", email)
                )
        );
    }

    public User create(User user) {
        logger.info("Inside UserDao create method");
        return persist(user);
    }

    public List<User> getAllUsers(){
        return list(
                namedTypedQuery("org.aumni.core.User.getAllUsers")
        );
    }

}
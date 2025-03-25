package org.aumni;

import com.google.inject.AbstractModule;
import org.aumni.dao.EmployeeDao;
import org.aumni.dao.EmployeeDaoImpl;
import org.aumni.dao.UserDao;
import org.aumni.dao.UserDaoImpl;
import org.aumni.services.EmployeeService;
import org.aumni.services.EmployeeServiceImpl;
import org.aumni.services.UserService;
import org.aumni.services.UserServiceImpl;
import org.hibernate.SessionFactory;

public class GuiceModule extends AbstractModule {

    private final SessionFactory sessionFactory;
    public GuiceModule(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

    @Override
    protected void configure(){

        bind(EmployeeService.class).to(EmployeeServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(EmployeeDao.class).to(EmployeeDaoImpl.class);
        bind(SessionFactory.class).toInstance(sessionFactory);

    }
}

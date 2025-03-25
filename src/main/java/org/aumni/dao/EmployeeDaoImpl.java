package org.aumni.dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.aumni.core.Employee;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeDaoImpl extends AbstractDAO<Employee> implements EmployeeDao{

    private static final Logger logger= LoggerFactory.getLogger(EmployeeDao.class);

    @Inject
    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Employee create(Employee employee) {
        logger.info("Inside EmployeeDao create method");
        return persist(employee);
    }

}

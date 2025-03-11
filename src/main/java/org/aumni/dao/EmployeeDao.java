package org.aumni.dao;

import org.aumni.core.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmployeeDao extends AbstractDAO<Employee> {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeDao.class);
    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Employee create(Employee employee) {
        logger.info("Inside EmployeeDao create method");
        return persist(employee);
    }
}


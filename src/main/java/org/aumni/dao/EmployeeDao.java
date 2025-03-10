package org.aumni.dao;

import org.aumni.core.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

public class EmployeeDao extends AbstractDAO<Employee> {

    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Employee create(Employee employee) {
        return persist(employee);
    }
}


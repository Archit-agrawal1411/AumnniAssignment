package org.aumni.services;

import com.google.inject.Inject;
import org.aumni.core.Employee;
import org.aumni.dao.EmployeeDao;

public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDao employeeDao;

    @Inject
    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }

    @Override
    public Employee create(Employee employee) {
        return employeeDao.create(employee);
    }
}

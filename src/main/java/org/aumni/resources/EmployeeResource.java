package org.aumni.resources;

import org.aumni.core.Employee;
import org.aumni.core.User;
import org.aumni.dao.EmployeeDao;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final EmployeeDao employeeDao;

    public EmployeeResource(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @POST
    @UnitOfWork
    @RolesAllowed("Admin")
    public Response createEmployee(@Auth User user, @Valid Employee employee) {

        Employee createdEmployee = employeeDao.create(employee);
        return Response.status(Response.Status.CREATED).entity(createdEmployee).build();
    }
}
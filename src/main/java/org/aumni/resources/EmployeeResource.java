package org.aumni.resources;

import com.mysql.cj.exceptions.UnableToConnectException;
import org.aumni.core.Employee;
import org.aumni.core.User;
import org.aumni.dao.EmployeeDao;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger= LoggerFactory.getLogger(EmployeeResource.class);
    private final EmployeeDao employeeDao;

    public EmployeeResource(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @POST
    @UnitOfWork
    @RolesAllowed("Admin")
    public Response createEmployee(@Auth User user, @Valid Employee employee) {
        try{
            logger.info("Inside Employee Resource createEmployee class /employees endpoint hit");
            Employee createdEmployee = employeeDao.create(employee);
            return Response.status(Response.Status.CREATED).entity(createdEmployee).build();
        } catch (UnableToConnectException e) {
            logger.error("Unable to connect to the database during employee registration");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occured during Employee registration")
                    .build();
        }catch (Exception e) {
            logger.error("An unknown error occured during employee registration");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unknown error occured during employee registration")
                    .build();
        }
    }
}
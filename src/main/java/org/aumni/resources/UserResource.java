package org.aumni.resources;

import com.mysql.cj.exceptions.UnableToConnectException;
import org.aumni.core.User;
import org.aumni.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;

    private static final Logger logger= LoggerFactory.getLogger(UserResource.class);
    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @POST
    @UnitOfWork
    public Response createAdminUser(@Valid User user) {
        try {
            logger.info("inside User resource class /users end point called");
            user.setRole("Admin");
            User createdUser = userDao.create(user);
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        }catch (UnableToConnectException e){
            logger.error("Unable to connect to the database user registration failed");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unable to connect to database user registration failed")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An Unknown error occured during user creation")
                    .build();
        }
    }
}
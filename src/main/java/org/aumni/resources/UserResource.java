package org.aumni.resources;

import com.google.inject.Inject;
import com.mysql.cj.exceptions.UnableToConnectException;
import io.dropwizard.hibernate.UnitOfWork;
import org.aumni.core.User;
import org.aumni.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;
    private static final Logger logger= LoggerFactory.getLogger(UserResource.class);


    @Inject
    public UserResource(UserService userService){
        this.userService=userService;
    }

    @POST
    @UnitOfWork
    public Response createAdminUser(@Valid User user) {
        try {
            logger.info("inside User resource class /users end point called");
            user.setRole("Admin");
            User createdUser = userService.create(user);
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
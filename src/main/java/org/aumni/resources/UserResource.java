package org.aumni.resources;

import org.aumni.core.User;
import org.aumni.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @POST
    @UnitOfWork
    public Response createAdminUser(@Valid User user) {

        user.setRole("Admin");
        User createdUser = userDao.create(user);
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }
}
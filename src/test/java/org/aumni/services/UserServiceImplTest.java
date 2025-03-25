package org.aumni.services;

import org.aumni.core.User;
import org.aumni.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void testFindByEmail_UserFound() {

        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));


        Optional<User> result = userService.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }
    /*
    To Summarize the Test Flow:
        Mocking userDao.findByEmail:

        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));
        This tells Mockito that when findByEmail(email) is called on userDao with the
        specific email, it should return an Optional containing the user.

        Calling the Service Method:

        Optional<User> result = userService.findByEmail(email);
        Here, userService.findByEmail(email) calls userDao.findByEmail(email) under the hood.
        Thanks to the mock, this call will return Optional.of(user) as defined above.

        Assertions:
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        You’re verifying that:

        The result is present (i.e., the Optional<User> is not empty).
        The email of the returned User matches the email you passed to userService.
     */

    @Test
    public void testFindByEmail_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userDao.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail(email);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setEmail("newuser@example.com");
        when(userDao.create(user)).thenReturn(user);


        User createdUser = userService.create(user);
        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
    }

    @Test
    public void testGetAllUsers() {

        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");

        when(userDao.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("user1@example.com", users.get(0).getEmail());
        assertEquals("user2@example.com", users.get(1).getEmail());
    }
}

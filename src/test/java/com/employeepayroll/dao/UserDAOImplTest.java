package com.employeepayroll.dao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.User;

public class UserDAOImplTest {

    private final UserDAO userDAO = new UserDAOImpl();

    @Test
    void testAddUserAndGetUserById() throws Exception {

        String username = "testuser_" + System.currentTimeMillis();

        User user = new User(
                0,
                username,
                "test_hash",
                "ADMIN",
                null
        );

        userDAO.addUser(user);

        assertTrue(user.getUserId() > 0);

        User savedUser = userDAO.getUserById(user.getUserId());

        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals("test_hash", savedUser.getPasswordHash());
        assertEquals("ADMIN", savedUser.getRole());
        assertNull(savedUser.getEmployeeId());

        userDAO.deleteUser(user.getUserId());
    }

    @Test
    void testGetUserByUsername() throws Exception {

        String username = "username_test_" + System.currentTimeMillis();

        User user = new User(
                0,
                username,
                "test_hash",
                "ADMIN",
                null
        );

        userDAO.addUser(user);

        User foundUser = userDAO.getUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals(user.getUserId(), foundUser.getUserId());
        assertEquals(username, foundUser.getUsername());

        userDAO.deleteUser(user.getUserId());
    }

    @Test
    void testGetAllUsers() throws Exception {

        String username = "allusers_test_" + System.currentTimeMillis();

        User user = new User(
                0,
                username,
                "test_hash",
                "ADMIN",
                null
        );

        userDAO.addUser(user);

        List<User> users = userDAO.getAllUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());

        boolean userFound = users.stream()
                .anyMatch(existingUser ->
                        existingUser.getUserId() == user.getUserId());

        assertTrue(userFound);

        userDAO.deleteUser(user.getUserId());
    }

    @Test
    void testUpdateUser() throws Exception {

        String username = "update_test_" + System.currentTimeMillis();
        String updatedUsername = username + "_updated";

        User user = new User(
                0,
                username,
                "test_hash",
                "ADMIN",
                null
        );

        userDAO.addUser(user);

        user.setUsername(updatedUsername);
        user.setPasswordHash("updated_hash");

        boolean updated = userDAO.updateUser(user);

        assertTrue(updated);

        User updatedUser = userDAO.getUserById(user.getUserId());

        assertNotNull(updatedUser);
        assertEquals(updatedUsername, updatedUser.getUsername());
        assertEquals("updated_hash", updatedUser.getPasswordHash());

        userDAO.deleteUser(user.getUserId());
    }

    @Test
    void testDeleteUser() throws Exception {

        String username = "delete_test_" + System.currentTimeMillis();

        User user = new User(
                0,
                username,
                "test_hash",
                "ADMIN",
                null
        );

        userDAO.addUser(user);

        int userId = user.getUserId();

        boolean deleted = userDAO.deleteUser(userId);

        assertTrue(deleted);

        User deletedUser = userDAO.getUserById(userId);

        assertNull(deletedUser);
    }

    @Test
    void testGetUserByIdReturnsNullForInvalidId() throws Exception {

        User user = userDAO.getUserById(-999999);

        assertNull(user);
    }

    @Test
    void testGetUserByUsernameReturnsNullForNonExistingUsername()
            throws Exception {

        User user = userDAO.getUserByUsername(
                "does_not_exist_" + System.currentTimeMillis());

        assertNull(user);
    }
}
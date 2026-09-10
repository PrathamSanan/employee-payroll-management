package com.employeepayroll.service;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.dao.UserDAO;
import com.employeepayroll.model.User;

public class UserServiceImplTest {

    @Test
    void testAddUser() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        userService.addUser(user);

        assertTrue(testUserDAO.addUserCalled);
        assertSame(user, testUserDAO.savedUser);
    }

    @Test
    void testGetUserById() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();

        User expectedUser = new User(
                1,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        testUserDAO.userToReturn = expectedUser;

        UserService userService = new UserServiceImpl(testUserDAO);

        User result = userService.getUserById(1);

        assertSame(expectedUser, result);
        assertEquals(1, testUserDAO.requestedUserId);
    }

    @Test
    void testGetUserByIdRejectsInvalidId() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(0)
        );

        assertFalse(testUserDAO.getUserByIdCalled);
    }

    @Test
    void testGetUserByUsername() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();

        User expectedUser = new User(
                1,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        testUserDAO.userToReturn = expectedUser;

        UserService userService = new UserServiceImpl(testUserDAO);

        User result = userService.getUserByUsername("admin");

        assertSame(expectedUser, result);
        assertEquals("admin", testUserDAO.requestedUsername);
    }

    @Test
    void testGetUserByUsernameRejectsBlankUsername() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserByUsername(" ")
        );

        assertFalse(testUserDAO.getUserByUsernameCalled);
    }

    @Test
    void testGetAllUsers() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();

        List<User> expectedUsers = List.of(
                new User(1, "admin", "hash1", "ADMIN", null),
                new User(2, "employee", "hash2", "EMPLOYEE", 14)
        );

        testUserDAO.usersToReturn = expectedUsers;

        UserService userService = new UserServiceImpl(testUserDAO);

        List<User> result = userService.getAllUsers();

        assertSame(expectedUsers, result);
        assertTrue(testUserDAO.getAllUsersCalled);
    }

    @Test
    void testUpdateUser() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();

        User user = new User(
                1,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        UserService userService = new UserServiceImpl(testUserDAO);

        boolean result = userService.updateUser(user);

        assertTrue(result);
        assertTrue(testUserDAO.updateUserCalled);
        assertSame(user, testUserDAO.savedUser);
    }

    @Test
    void testUpdateUserRejectsInvalidId() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateUser(user)
        );

        assertFalse(testUserDAO.updateUserCalled);
    }

    @Test
    void testDeleteUser() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        boolean result = userService.deleteUser(1);

        assertTrue(result);
        assertTrue(testUserDAO.deleteUserCalled);
        assertEquals(1, testUserDAO.requestedUserId);
    }

    @Test
    void testDeleteUserRejectsInvalidId() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(0)
        );

        assertFalse(testUserDAO.deleteUserCalled);
    }

    @Test
    void testAddUserRejectsNullUser() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(null)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    @Test
    void testAddUserRejectsBlankUsername() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                " ",
                "hashed_password",
                "ADMIN",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(user)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    @Test
    void testAddUserRejectsBlankPasswordHash() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "admin",
                " ",
                "ADMIN",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(user)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    @Test
    void testAddUserRejectsInvalidRole() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "admin",
                "hashed_password",
                "MANAGER",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(user)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    @Test
    void testEmployeeRoleRequiresEmployeeId() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "employee",
                "hashed_password",
                "EMPLOYEE",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(user)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    @Test
    void testAdminRoleCanHaveNullEmployeeId() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "admin",
                "hashed_password",
                "ADMIN",
                null
        );

        userService.addUser(user);

        assertTrue(testUserDAO.addUserCalled);
    }

    @Test
    void testEmployeeRoleWithEmployeeIdIsAccepted() throws Exception {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "employee",
                "hashed_password",
                "EMPLOYEE",
                14
        );

        userService.addUser(user);

        assertTrue(testUserDAO.addUserCalled);
    }

    @Test
    void testRejectsInvalidEmployeeId() {

        TestUserDAO testUserDAO = new TestUserDAO();
        UserService userService = new UserServiceImpl(testUserDAO);

        User user = new User(
                0,
                "employee",
                "hashed_password",
                "EMPLOYEE",
                0
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(user)
        );

        assertFalse(testUserDAO.addUserCalled);
    }

    private static class TestUserDAO implements UserDAO {

        private boolean addUserCalled;
        private boolean getUserByIdCalled;
        private boolean getUserByUsernameCalled;
        private boolean getAllUsersCalled;
        private boolean updateUserCalled;
        private boolean deleteUserCalled;

        private int requestedUserId;
        private String requestedUsername;

        private User savedUser;
        private User userToReturn;
        private List<User> usersToReturn = List.of();

        @Override
        public void addUser(User user) throws SQLException {
            addUserCalled = true;
            savedUser = user;
        }

        @Override
        public User getUserById(int userId) throws SQLException {
            getUserByIdCalled = true;
            requestedUserId = userId;
            return userToReturn;
        }

        @Override
        public User getUserByUsername(String username) throws SQLException {
            getUserByUsernameCalled = true;
            requestedUsername = username;
            return userToReturn;
        }

        @Override
        public List<User> getAllUsers() throws SQLException {
            getAllUsersCalled = true;
            return usersToReturn;
        }

        @Override
        public boolean updateUser(User user) throws SQLException {
            updateUserCalled = true;
            savedUser = user;
            return true;
        }

        @Override
        public boolean deleteUser(int userId) throws SQLException {
            deleteUserCalled = true;
            requestedUserId = userId;
            return true;
        }
    }
}
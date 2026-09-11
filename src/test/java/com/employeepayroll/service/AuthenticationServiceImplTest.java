package com.employeepayroll.service;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.employeepayroll.dao.UserDAO;
import com.employeepayroll.model.User;
import com.employeepayroll.util.PasswordUtil;

class AuthenticationServiceImplTest {

    private TestUserDAO testUserDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        testUserDAO = new TestUserDAO();
        authenticationService = new AuthenticationServiceImpl(testUserDAO);
    }

    @Test
    void testLoginWithValidCredentials() throws SQLException {

        String password = "Admin@123";

        User user = new User(
                1,
                "admin",
                PasswordUtil.hashPassword(password),
                "ADMIN",
                null
        );

        testUserDAO.user = user;

        User result = authenticationService.login(
                "admin",
                password
        );

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void testLoginWithWrongPassword() throws SQLException {

        String correctPassword = "Admin@123";

        User user = new User(
                1,
                "admin",
                PasswordUtil.hashPassword(correctPassword),
                "ADMIN",
                null
        );

        testUserDAO.user = user;

        User result = authenticationService.login(
                "admin",
                "WrongPassword"
        );

        assertNull(result);
    }

    @Test
    void testLoginWithUnknownUsername() throws SQLException {

        User result = authenticationService.login(
                "unknown",
                "Admin@123"
        );

        assertNull(result);
    }

    @Test
    void testLoginWithNullUsername() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        null,
                        "Admin@123"
                )
        );

        assertEquals(0, testUserDAO.getUserByUsernameCalls);
    }

    @Test
    void testLoginWithBlankUsername() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "   ",
                        "Admin@123"
                )
        );

        assertEquals(0, testUserDAO.getUserByUsernameCalls);
    }

    @Test
    void testLoginWithNullPassword() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "admin",
                        null
                )
        );

        assertEquals(0, testUserDAO.getUserByUsernameCalls);
    }

    @Test
    void testLoginWithBlankPassword() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "admin",
                        "   "
                )
        );

        assertEquals(0, testUserDAO.getUserByUsernameCalls);
    }

    @Test
    void testLoginDelegatesUsernameLookupToDAO() throws SQLException {

        String password = "Admin@123";

        User user = new User(
                1,
                "admin",
                PasswordUtil.hashPassword(password),
                "ADMIN",
                null
        );

        testUserDAO.user = user;

        authenticationService.login(
                "admin",
                password
        );

        assertEquals(
                "admin",
                testUserDAO.lastUsername
        );

        assertEquals(
                1,
                testUserDAO.getUserByUsernameCalls
        );
    }

    private static class TestUserDAO implements UserDAO {

        private User user;
        private String lastUsername;
        private int getUserByUsernameCalls;

        @Override
        public void addUser(User user) {
            this.user = user;
        }

        @Override
        public User getUserById(int userId) {
            return user;
        }

        @Override
        public User getUserByUsername(String username) {

            getUserByUsernameCalls++;
            lastUsername = username;

            if (user != null &&
                    user.getUsername().equals(username)) {

                return user;
            }

            return null;
        }

        @Override
        public java.util.List<User> getAllUsers() {
            return java.util.List.of();
        }

        @Override
        public boolean updateUser(User user) {
            this.user = user;
            return true;
        }

        @Override
        public boolean deleteUser(int userId) {
            this.user = null;
            return true;
        }
    }
}
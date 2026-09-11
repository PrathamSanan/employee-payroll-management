package com.employeepayroll.service;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.dao.UserDAO;
import com.employeepayroll.dao.UserDAOImpl;
import com.employeepayroll.model.User;
import com.employeepayroll.util.PasswordUtil;

class AuthenticationServiceIntegrationTest {

    @Test
    void testLoginAgainstDatabase() throws SQLException {

        UserDAO userDAO = new UserDAOImpl();
        AuthenticationService authenticationService =
                new AuthenticationServiceImpl(userDAO);

        String username =
                "auth_test_" + System.currentTimeMillis();

        String password = "Admin@123";

        User testUser = new User(
                0,
                username,
                PasswordUtil.hashPassword(password),
                "ADMIN",
                null
        );

        try {
            // Insert test user into the real database
            userDAO.addUser(testUser);

            assertTrue(testUser.getUserId() > 0);

            // Test successful authentication
            User authenticatedUser =
                    authenticationService.login(
                            username,
                            password
                    );

            assertNotNull(authenticatedUser);
            assertEquals(
                    username,
                    authenticatedUser.getUsername()
            );
            assertEquals(
                    "ADMIN",
                    authenticatedUser.getRole()
            );

            // Test incorrect password
            User failedLogin =
                    authenticationService.login(
                            username,
                            "WrongPassword"
                    );

            assertNull(failedLogin);

        } finally {

            // Always clean up the test user
            if (testUser.getUserId() > 0) {
                userDAO.deleteUser(testUser.getUserId());
            }
        }
    }
}
package com.employeepayroll.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.User;

class AuthorizationServiceImplTest {

    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = new AuthorizationServiceImpl();
    }

    @Test
    void testAdminUserIsAdmin() {

        User user = createUser("ADMIN");

        assertTrue(
                authorizationService.isAdmin(user)
        );
    }

    @Test
    void testEmployeeUserIsNotAdmin() {

        User user = createUser("EMPLOYEE");

        assertFalse(
                authorizationService.isAdmin(user)
        );
    }

    @Test
    void testEmployeeUserIsEmployee() {

        User user = createUser("EMPLOYEE");

        assertTrue(
                authorizationService.isEmployee(user)
        );
    }

    @Test
    void testAdminUserIsNotEmployee() {

        User user = createUser("ADMIN");

        assertFalse(
                authorizationService.isEmployee(user)
        );
    }

    @Test
    void testNullUserIsNotAdmin() {

        assertFalse(
                authorizationService.isAdmin(null)
        );
    }

    @Test
    void testNullUserIsNotEmployee() {

        assertFalse(
                authorizationService.isEmployee(null)
        );
    }

    @Test
    void testAdminCanPassAdminRequirement() {

        User user = createUser("ADMIN");

        assertDoesNotThrow(
                () -> authorizationService.requireAdmin(user)
        );
    }

    @Test
    void testEmployeeCannotPassAdminRequirement() {

        User user = createUser("EMPLOYEE");

        assertThrows(
                SecurityException.class,
                () -> authorizationService.requireAdmin(user)
        );
    }

    @Test
    void testEmployeeCanPassEmployeeRequirement() {

        User user = createUser("EMPLOYEE");

        assertDoesNotThrow(
                () -> authorizationService.requireEmployee(user)
        );
    }

    @Test
    void testAdminCannotPassEmployeeRequirement() {

        User user = createUser("ADMIN");

        assertThrows(
                SecurityException.class,
                () -> authorizationService.requireEmployee(user)
        );
    }

    @Test
    void testNullUserFailsAdminRequirement() {

        assertThrows(
                SecurityException.class,
                () -> authorizationService.requireAdmin(null)
        );
    }

    @Test
    void testNullUserFailsEmployeeRequirement() {

        assertThrows(
                SecurityException.class,
                () -> authorizationService.requireEmployee(null)
        );
    }

    private User createUser(String role) {

        return new User(
                1,
                "testuser",
                "test_hash",
                role,
                null
        );
    }
}
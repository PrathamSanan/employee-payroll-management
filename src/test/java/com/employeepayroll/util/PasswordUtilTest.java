package com.employeepayroll.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PasswordUtilTest {

    @Test
    void testHashPassword() {

        String password = "Test@123";

        String hash = PasswordUtil.hashPassword(password);

        assertNotNull(hash);
        assertFalse(hash.isBlank());
        assertNotEquals(password, hash);
    }

    @Test
    void testVerifyCorrectPassword() {

        String password = "Test@123";

        String hash = PasswordUtil.hashPassword(password);

        assertTrue(
                PasswordUtil.verifyPassword(password, hash)
        );
    }

    @Test
    void testVerifyIncorrectPassword() {

        String password = "Test@123";

        String hash = PasswordUtil.hashPassword(password);

        assertFalse(
                PasswordUtil.verifyPassword(
                        "WrongPassword",
                        hash
                )
        );
    }

    @Test
    void testSamePasswordProducesDifferentHashes() {

        String password = "Test@123";

        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);

        assertNotEquals(hash1, hash2);

        assertTrue(
                PasswordUtil.verifyPassword(password, hash1)
        );

        assertTrue(
                PasswordUtil.verifyPassword(password, hash2)
        );
    }

    @Test
    void testHashPasswordRejectsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordUtil.hashPassword(null)
        );
    }

    @Test
    void testHashPasswordRejectsBlankPassword() {

        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordUtil.hashPassword(" ")
        );
    }

    @Test
    void testVerifyNullPassword() {

        String hash = PasswordUtil.hashPassword("Test@123");

        assertFalse(
                PasswordUtil.verifyPassword(null, hash)
        );
    }

    @Test
    void testVerifyNullHash() {

        assertFalse(
                PasswordUtil.verifyPassword(
                        "Test@123",
                        null
                )
        );
    }

    @Test
    void testVerifyBlankHash() {

        assertFalse(
                PasswordUtil.verifyPassword(
                        "Test@123",
                        " "
                )
        );
    }
}
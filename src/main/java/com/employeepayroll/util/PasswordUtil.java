package com.employeepayroll.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String plainPassword) {

        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException(
                    "Password cannot be blank"
            );
        }

        return BCrypt.hashpw(
                plainPassword,
                BCrypt.gensalt()
        );
    }

    public static boolean verifyPassword(
            String plainPassword,
            String passwordHash) {

        if (plainPassword == null || plainPassword.isBlank()) {
            return false;
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            return false;
        }

        return BCrypt.checkpw(
                plainPassword,
                passwordHash
        );
    }
}
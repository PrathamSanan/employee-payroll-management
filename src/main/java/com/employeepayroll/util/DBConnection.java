package com.employeepayroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            getRequiredEnvironmentVariable("DB_URL");

    private static final String USER =
            getRequiredEnvironmentVariable("DB_USERNAME");

    private static final String PASSWORD =
            getRequiredEnvironmentVariable("DB_PASSWORD");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static String getRequiredEnvironmentVariable(String name) {

        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable is missing: " + name
            );
        }

        return value;
    }
}

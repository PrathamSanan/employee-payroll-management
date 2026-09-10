package com.employeepayroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.model.User;
import com.employeepayroll.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) throws SQLException {

        String sql = """
                INSERT INTO users
                (username, password_hash, role, employee_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole());

            if (user.getEmployeeId() != null) {
                statement.setInt(4, user.getEmployeeId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public User getUserById(int userId) throws SQLException {

        String sql = """
                SELECT user_id, username, password_hash, role, employee_id
                FROM users
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {

        String sql = """
                SELECT user_id, username, password_hash, role, employee_id
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {

        String sql = """
                SELECT user_id, username, password_hash, role, employee_id
                FROM users
                ORDER BY user_id
                """;

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        }

        return users;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {

        String sql = """
                UPDATE users
                SET username = ?,
                    password_hash = ?,
                    role = ?,
                    employee_id = ?
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole());

            if (user.getEmployeeId() != null) {
                statement.setInt(4, user.getEmployeeId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setInt(5, user.getUserId());

            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setRole(resultSet.getString("role"));

        int employeeId = resultSet.getInt("employee_id");

        if (resultSet.wasNull()) {
            user.setEmployeeId(null);
        } else {
            user.setEmployeeId(employeeId);
        }

        return user;
    }
}

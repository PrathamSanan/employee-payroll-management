package com.employeepayroll.service;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.dao.UserDAO;
import com.employeepayroll.dao.UserDAOImpl;
import com.employeepayroll.model.User;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) throws SQLException {
        validateUser(user, false);
        userDAO.addUser(user);
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "User ID must be greater than 0");
        }

        return userDAO.getUserById(userId);
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        validateUsername(username);
        return userDAO.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        validateUser(user, true);
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException(
                    "User ID must be greater than 0");
        }

        return userDAO.deleteUser(userId);
    }

    private void validateUser(User user, boolean isUpdate) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (isUpdate && user.getUserId() <= 0) {
            throw new IllegalArgumentException(
                    "User ID must be greater than 0");
        }

        validateUsername(user.getUsername());

        if (user.getPasswordHash() == null ||
                user.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException(
                    "Password hash cannot be blank");
        }

        validateRole(user.getRole());

        if ("EMPLOYEE".equals(user.getRole())
                && user.getEmployeeId() == null) {
            throw new IllegalArgumentException(
                    "Employee ID is required for EMPLOYEE role");
        }

        if (user.getEmployeeId() != null
                && user.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0");
        }
    }

    private void validateUsername(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username cannot be blank");
        }
    }

    private void validateRole(String role) {

        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException(
                    "Role cannot be blank");
        }

        if (!"ADMIN".equals(role) && !"EMPLOYEE".equals(role)) {
            throw new IllegalArgumentException(
                    "Role must be ADMIN or EMPLOYEE");
        }
    }
}
package com.employeepayroll.service;

import java.sql.SQLException;

import com.employeepayroll.dao.UserDAO;
import com.employeepayroll.dao.UserDAOImpl;
import com.employeepayroll.model.User;
import com.employeepayroll.util.PasswordUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public AuthenticationServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User login(String username, String password) throws SQLException {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username cannot be blank"
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Password cannot be blank"
            );
        }

        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        if (!PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash())) {

            return null;
        }

        return user;
    }
}
package com.employeepayroll.service;

import java.sql.SQLException;

import com.employeepayroll.model.User;

public interface AuthenticationService {

    User login(String username, String password) throws SQLException;
}
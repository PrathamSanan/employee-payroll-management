package com.employeepayroll.dao;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.model.User;

public interface UserDAO {

    void addUser(User user) throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;
}
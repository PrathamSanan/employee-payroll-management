package com.employeepayroll.service;

import com.employeepayroll.model.User;

public interface AuthorizationService {

    boolean isAdmin(User user);

    boolean isEmployee(User user);

    void requireAdmin(User user);

    void requireEmployee(User user);
}
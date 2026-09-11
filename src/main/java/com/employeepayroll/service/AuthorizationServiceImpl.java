package com.employeepayroll.service;

import com.employeepayroll.model.User;

public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public boolean isAdmin(User user) {

        return user != null
                && "ADMIN".equals(user.getRole());
    }

    @Override
    public boolean isEmployee(User user) {

        return user != null
                && "EMPLOYEE".equals(user.getRole());
    }

    @Override
    public void requireAdmin(User user) {

        if (!isAdmin(user)) {
            throw new SecurityException(
                    "Administrator access required"
            );
        }
    }

    @Override
    public void requireEmployee(User user) {

        if (!isEmployee(user)) {
            throw new SecurityException(
                    "Employee access required"
            );
        }
    }
}
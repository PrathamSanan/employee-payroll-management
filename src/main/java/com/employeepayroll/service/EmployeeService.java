package com.employeepayroll.service;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.model.Employee;

public interface EmployeeService {

    void addEmployee(Employee employee) throws SQLException;

    Employee getEmployeeById(int employeeId) throws SQLException;

    List<Employee> getAllEmployees() throws SQLException;

    List<Employee> searchEmployees(String keyword) throws SQLException;

    boolean updateEmployee(Employee employee) throws SQLException;

    boolean deleteEmployee(int employeeId) throws SQLException;
}
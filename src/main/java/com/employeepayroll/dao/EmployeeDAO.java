package com.employeepayroll.dao;

import com.employeepayroll.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

    void addEmployee(Employee employee) throws SQLException;

    Employee getEmployeeById(int employeeId) throws SQLException;

    List<Employee> getAllEmployees() throws SQLException;

    boolean updateEmployee(Employee employee) throws SQLException;

    boolean deleteEmployee(int employeeId) throws SQLException;

    List<Employee> searchEmployees(String keyword) throws SQLException;
}
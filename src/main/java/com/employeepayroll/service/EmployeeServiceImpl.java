package com.employeepayroll.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.employeepayroll.dao.EmployeeDAO;
import com.employeepayroll.dao.EmployeeDAOImpl;
import com.employeepayroll.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {
        this.employeeDAO = new EmployeeDAOImpl();
    }

    // --------------------------addEmployee---------------------------------------------------

    @Override
    public void addEmployee(Employee employee) throws SQLException {

        validateEmployee(employee);

        employeeDAO.addEmployee(employee);
    }

    // --------------------------getEmployeeById---------------------------------------------------

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException {

        validateId(employeeId);

        return employeeDAO.getEmployeeById(employeeId);
    }

    // --------------------------getAllEmployees---------------------------------------------------

    @Override
    public List<Employee> getAllEmployees() throws SQLException {

        return employeeDAO.getAllEmployees();
    }

    // --------------------------searchEmployees---------------------------------------------------

    @Override
    public List<Employee> searchEmployees(String keyword)
            throws SQLException {

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(
                    "Search keyword cannot be blank"
            );
        }

        return employeeDAO.searchEmployees(keyword.trim());
    }

    // --------------------------updateEmployee---------------------------------------------------

    @Override
    public boolean updateEmployee(Employee employee)
            throws SQLException {

        validateEmployee(employee);

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }

        return employeeDAO.updateEmployee(employee);
    }

    // --------------------------deleteEmployee---------------------------------------------------

    @Override
    public boolean deleteEmployee(int employeeId)
            throws SQLException {

        validateId(employeeId);

        return employeeDAO.deleteEmployee(employeeId);
    }

    // --------------------------validateEmployee---------------------------------------------------

    private void validateEmployee(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null"
            );
        }

        if (employee.getFirstName() == null ||
                employee.getFirstName().isBlank()) {

            throw new IllegalArgumentException(
                    "First name cannot be blank"
            );
        }

        if (employee.getLastName() == null ||
                employee.getLastName().isBlank()) {

            throw new IllegalArgumentException(
                    "Last name cannot be blank"
            );
        }

        if (employee.getEmail() == null ||
                employee.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email cannot be blank"
            );
        }

        if (!employee.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "Invalid email format"
            );
        }

        if (employee.getPhone() == null ||
                employee.getPhone().isBlank()) {

            throw new IllegalArgumentException(
                    "Phone cannot be blank"
            );
        }

        if (employee.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }

        if (employee.getDesignation() == null ||
                employee.getDesignation().isBlank()) {

            throw new IllegalArgumentException(
                    "Designation cannot be blank"
            );
        }

        LocalDate joiningDate = employee.getJoiningDate();

        if (joiningDate == null) {
            throw new IllegalArgumentException(
                    "Joining date cannot be null"
            );
        }

        if (joiningDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Joining date cannot be in the future"
            );
        }

        BigDecimal salary = employee.getBasicSalary();

        if (salary == null ||
                salary.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Basic salary must be greater than 0"
            );
        }

        if (employee.getStatus() == null ||
                employee.getStatus().isBlank()) {

            throw new IllegalArgumentException(
                    "Status cannot be blank"
            );
        }

        String status = employee.getStatus().trim().toUpperCase();

        if (!status.equals("ACTIVE") &&
                !status.equals("INACTIVE")) {

            throw new IllegalArgumentException(
                    "Status must be ACTIVE or INACTIVE"
            );
        }
    }

    // --------------------------validateId---------------------------------------------------

    private void validateId(int employeeId) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be greater than 0"
            );
        }
    }
}
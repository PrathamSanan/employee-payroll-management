package com.employeepayroll.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Department;
import com.employeepayroll.model.Employee;

class EmployeeDAOImplTest {

    @Test
    void testEmployeeCRUD() throws Exception {

        DepartmentDAO departmentDAO = new DepartmentDAOImpl();
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        String uniqueValue =
                String.valueOf(System.currentTimeMillis());

        Department department = new Department(
                0,
                "Test Department " + uniqueValue,
                "Temporary department for Employee DAO testing"
        );

        departmentDAO.addDepartment(department);

        assertTrue(
                department.getDepartmentId() > 0,
                "Department should receive a generated ID"
        );

        Employee employee = new Employee(
                0,
                "Test",
                "Employee",
                "test.employee." + uniqueValue + "@example.com",
                "9999999999",
                department.getDepartmentId(),
                "Software Engineer",
                LocalDate.now(),
                new BigDecimal("50000.00"),
                "ACTIVE"
        );

        try {

            // -------------------------------------------------
            // CREATE EMPLOYEE
            // -------------------------------------------------

            employeeDAO.addEmployee(employee);

            assertTrue(
                    employee.getEmployeeId() > 0,
                    "Employee should receive a generated ID"
            );

            // -------------------------------------------------
            // READ EMPLOYEE BY ID
            // -------------------------------------------------

            Employee savedEmployee =
                    employeeDAO.getEmployeeById(
                            employee.getEmployeeId()
                    );

            assertNotNull(savedEmployee);

            assertEquals(
                    "Test",
                    savedEmployee.getFirstName()
            );

            assertEquals(
                    "Employee",
                    savedEmployee.getLastName()
            );

            assertEquals(
                    department.getDepartmentId(),
                    savedEmployee.getDepartmentId()
            );

            assertEquals(
                    new BigDecimal("50000.00"),
                    savedEmployee.getBasicSalary()
            );

            // -------------------------------------------------
            // READ ALL EMPLOYEES
            // -------------------------------------------------

            List<Employee> employees =
                    employeeDAO.getAllEmployees();

            assertFalse(
                    employees.isEmpty(),
                    "Employee list should not be empty"
            );

            boolean employeeExists =
                    employees.stream()
                            .anyMatch(
                                    e -> e.getEmployeeId()
                                            == employee.getEmployeeId()
                            );

            assertTrue(
                    employeeExists,
                    "Created employee should exist in employee list"
            );

            // -------------------------------------------------
            // UPDATE EMPLOYEE
            // -------------------------------------------------

            employee.setDesignation(
                    "Senior Software Engineer"
            );

            employee.setBasicSalary(
                    new BigDecimal("65000.00")
            );

            boolean updated =
                    employeeDAO.updateEmployee(employee);

            assertTrue(
                    updated,
                    "Employee should be updated successfully"
            );

            Employee updatedEmployee =
                    employeeDAO.getEmployeeById(
                            employee.getEmployeeId()
                    );

            assertNotNull(updatedEmployee);

            assertEquals(
                    "Senior Software Engineer",
                    updatedEmployee.getDesignation()
            );

            assertEquals(
                    new BigDecimal("65000.00"),
                    updatedEmployee.getBasicSalary()
            );

            // -------------------------------------------------
            // SEARCH EMPLOYEE
            // -------------------------------------------------

            List<Employee> searchResults =
                    employeeDAO.searchEmployees(
                            "Senior Software"
                    );

            boolean employeeFound =
                    searchResults.stream()
                            .anyMatch(
                                    e -> e.getEmployeeId()
                                            == employee.getEmployeeId()
                            );

            assertTrue(
                    employeeFound,
                    "Updated employee should appear in search results"
            );

            // -------------------------------------------------
            // DELETE EMPLOYEE
            // -------------------------------------------------

            boolean deleted =
                    employeeDAO.deleteEmployee(
                            employee.getEmployeeId()
                    );

            assertTrue(
                    deleted,
                    "Employee should be deleted successfully"
            );

            Employee deletedEmployee =
                    employeeDAO.getEmployeeById(
                            employee.getEmployeeId()
                    );

            assertNull(
                    deletedEmployee,
                    "Deleted employee should no longer exist"
            );

        } finally {

            // Employee must be deleted before Department
            // because employees.department_id is a foreign key.

            if (employee.getEmployeeId() > 0) {

                employeeDAO.deleteEmployee(
                        employee.getEmployeeId()
                );
            }

            if (department.getDepartmentId() > 0) {

                departmentDAO.deleteDepartment(
                        department.getDepartmentId()
                );
            }
        }
    }
}
package com.employeepayroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.model.Employee;
import com.employeepayroll.util.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO {

        private static final String INSERT_SQL =
                "INSERT INTO employees " +
                "(first_name, last_name, email, phone, department_id, " +
                "designation, joining_date, basic_salary, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String SELECT_BY_ID_SQL =
                "SELECT * FROM employees WHERE employee_id = ?";

        private static final String SELECT_ALL_SQL =
                "SELECT * FROM employees ORDER BY employee_id";

        private static final String UPDATE_SQL =
                "UPDATE employees SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "department_id = ?, " +
                "designation = ?, " +
                "joining_date = ?, " +
                "basic_salary = ?, " +
                "status = ? " +
                "WHERE employee_id = ?";

        private static final String DELETE_SQL =
                "DELETE FROM employees WHERE employee_id = ?";

        private static final String SEARCH_SQL =
                "SELECT * FROM employees " +
                "WHERE first_name LIKE ? " +
                "OR last_name LIKE ? " +
                "OR email LIKE ? " +
                "OR designation LIKE ? " +
                "ORDER BY employee_id";

//-----------------------addEmployee-----------------------------------------------
        @Override
        public void addEmployee(Employee employee) throws SQLException {
                try (Connection connection = DBConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(INSERT_SQL,Statement.RETURN_GENERATED_KEYS)) {

                        statement.setString(1, employee.getFirstName());
                        statement.setString(2, employee.getLastName());
                        statement.setString(3, employee.getEmail());
                        statement.setString(4, employee.getPhone());
                        statement.setInt(5, employee.getDepartmentId());
                        statement.setString(6, employee.getDesignation());
                        statement.setDate(
                                7,
                                java.sql.Date.valueOf(employee.getJoiningDate())
                        );
                        statement.setBigDecimal(8, employee.getBasicSalary());
                        statement.setString(9, employee.getStatus());

                        statement.executeUpdate();

                        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                        employee.setEmployeeId(
                                                generatedKeys.getInt(1)
                                        );
                                }
                        }
                }
        }

//-----------------------getEmployeeById-----------------------------------------------
        @Override
        public Employee getEmployeeById(int employeeId) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

                        statement.setInt(1, employeeId);

                        try (ResultSet resultSet = statement.executeQuery()) {

                                if (resultSet.next()) {
                                        Employee employee = mapResultSetToEmployee(resultSet);
                                        return employee;
                                }

                                return null;
                        }
                }
        }


//-------------------getAllEmployees-------------------------------------------------
        @Override
        public List<Employee> getAllEmployees() throws SQLException {

                List<Employee> employees = new ArrayList<>();

                try (Connection connection = DBConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                        ResultSet resultSet = statement.executeQuery()) {

                        while (resultSet.next()) {
                                Employee employee = mapResultSetToEmployee(resultSet);
                                employees.add(employee);
                        }
                }
                return employees;
        }
        // @Override
        // public List<Employee> getAllEmployees() throws SQLException {
        // List<Employee> employees = new ArrayList<>();
        // try (Connection connection = DBConnection.getConnection();
        //         PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
        //         ResultSet resultSet = statement.executeQuery()) {
        //                 while (resultSet.next()) {
        //                         Employee employee = new Employee();

        //                         employee.setEmployeeId(
        //                                 resultSet.getInt("employee_id")
        //                         );

        //                         employee.setFirstName(
        //                                 resultSet.getString("first_name")
        //                         );

        //                         employee.setLastName(
        //                                 resultSet.getString("last_name")
        //                         );

        //                         employee.setEmail(
        //                                 resultSet.getString("email")
        //                         );

        //                         employee.setPhone(
        //                                 resultSet.getString("phone")
        //                         );

        //                         employee.setDepartmentId(
        //                                 resultSet.getInt("department_id")
        //                         );

        //                         employee.setDesignation(
        //                                 resultSet.getString("designation")
        //                         );

        //                         employee.setJoiningDate(
        //                                 resultSet.getDate("joining_date").toLocalDate()
        //                         );

        //                         employee.setBasicSalary(
        //                                 resultSet.getBigDecimal("basic_salary")
        //                         );

        //                         employee.setStatus(
        //                                 resultSet.getString("status")
        //                         );

        //                         employees.add(employee);
        //                 }
        //         }

        // return employees;
        // }





//---------------------------updateEmployee---------------------------------------------
        @Override
        public boolean updateEmployee(Employee employee) throws SQLException {

                try (Connection connection = DBConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

                        statement.setString(1, employee.getFirstName());
                        statement.setString(2, employee.getLastName());
                        statement.setString(3, employee.getEmail());
                        statement.setString(4, employee.getPhone());
                        statement.setInt(5, employee.getDepartmentId());
                        statement.setString(6, employee.getDesignation());

                        statement.setDate(
                                7,
                                java.sql.Date.valueOf(employee.getJoiningDate())
                        );

                        statement.setBigDecimal(
                                8,
                                employee.getBasicSalary()
                        );

                        statement.setString(9, employee.getStatus());

                        statement.setInt(
                                10,
                                employee.getEmployeeId()
                        );

                        return statement.executeUpdate() > 0;
                }
        }


//-------------------------------deleteEmployee---------------------------------------------
        @Override
        public boolean deleteEmployee(int employeeId) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

                        statement.setInt(1, employeeId);
                        return statement.executeUpdate() > 0;
                }
        }



//---------------------------searchEmployees--------------------------------------------
        @Override
        public List<Employee> searchEmployees(String keyword) throws SQLException {
                List<Employee> employees = new ArrayList<>();
                String searchPattern = "%" + keyword + "%";

                try (Connection connection = DBConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(SEARCH_SQL)) {

                        statement.setString(1, searchPattern);
                        statement.setString(2, searchPattern);
                        statement.setString(3, searchPattern);
                        statement.setString(4, searchPattern);

                        try (ResultSet resultSet = statement.executeQuery()) {

                                while (resultSet.next()) {
                                        Employee employee = mapResultSetToEmployee(resultSet);
                                        employees.add(employee);
                                }
                        }
                }

                return employees;
        }
        // @Override
        // public List<Employee> searchEmployees(String keyword) throws SQLException {

        //         List<Employee> employees = new ArrayList<>();

        //         String searchPattern = "%" + keyword + "%";

        //         try (Connection connection = DBConnection.getConnection();
        //                 PreparedStatement statement = connection.prepareStatement(SEARCH_SQL)) {

        //                 statement.setString(1, searchPattern);
        //                 statement.setString(2, searchPattern);
        //                 statement.setString(3, searchPattern);
        //                 statement.setString(4, searchPattern);

        //                 try (ResultSet resultSet = statement.executeQuery()) {

        //                         while (resultSet.next()) {

        //                                 Employee employee = new Employee();

        //                                 employee.setEmployeeId(
        //                                         resultSet.getInt("employee_id")
        //                                 );

        //                                 employee.setFirstName(
        //                                         resultSet.getString("first_name")
        //                                 );

        //                                 employee.setLastName(
        //                                         resultSet.getString("last_name")
        //                                 );

        //                                 employee.setEmail(
        //                                         resultSet.getString("email")
        //                                 );

        //                                 employee.setPhone(
        //                                         resultSet.getString("phone")
        //                                 );

        //                                 employee.setDepartmentId(
        //                                         resultSet.getInt("department_id")
        //                                 );

        //                                 employee.setDesignation(
        //                                         resultSet.getString("designation")
        //                                 );

        //                                 employee.setJoiningDate(
        //                                         resultSet.getDate("joining_date").toLocalDate()
        //                                 );

        //                                 employee.setBasicSalary(
        //                                         resultSet.getBigDecimal("basic_salary")
        //                                 );

        //                                 employee.setStatus(
        //                                         resultSet.getString("status")
        //                                 );

        //                                 employees.add(employee);
        //                         }
        //                 }
        //         }

        // return employees;
        // }



//-------------------------------mapResultSetToEmployee---------------------------------------------
//-------------------------------helper function to map ResultSet-----------------------------------

        private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
                Employee employee = new Employee();

                employee.setEmployeeId(
                        resultSet.getInt("employee_id")
                );

                employee.setFirstName(
                        resultSet.getString("first_name")
                );

                employee.setLastName(
                        resultSet.getString("last_name")
                );

                employee.setEmail(
                        resultSet.getString("email")
                );

                employee.setPhone(
                        resultSet.getString("phone")
                );

                employee.setDepartmentId(
                        resultSet.getInt("department_id")
                );

                employee.setDesignation(
                        resultSet.getString("designation")
                );

                employee.setJoiningDate(
                        resultSet.getDate("joining_date").toLocalDate()
                );

                employee.setBasicSalary(
                        resultSet.getBigDecimal("basic_salary")
                );

                employee.setStatus(
                        resultSet.getString("status")
                );
        return employee;
        }



}
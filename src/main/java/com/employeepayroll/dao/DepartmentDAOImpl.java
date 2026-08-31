package com.employeepayroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.model.Department;
import com.employeepayroll.util.DBConnection;

public class DepartmentDAOImpl implements DepartmentDAO {

    private static final String INSERT_SQL =
            "INSERT INTO departments (department_name, description) VALUES (?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT department_id, department_name, description " +
            "FROM departments WHERE department_id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT department_id, department_name, description " +
            "FROM departments ORDER BY department_id";

    private static final String UPDATE_SQL =
            "UPDATE departments " +
            "SET department_name = ?, description = ? " +
            "WHERE department_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM departments WHERE department_id = ?";

    @Override
public void addDepartment(Department department) throws SQLException {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(
                         INSERT_SQL,
                         Statement.RETURN_GENERATED_KEYS)) {

        statement.setString(1, department.getDepartmentName());
        statement.setString(2, department.getDescription());

        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

            if (generatedKeys.next()) {
                department.setDepartmentId(
                        generatedKeys.getInt(1)
                );
            }
        }
    }
}

    @Override
    public Department getDepartmentById(int departmentId) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToDepartment(resultSet);
                }
            }
        }

        return null;
    }

    @Override
    public List<Department> getAllDepartments() throws SQLException {

        List<Department> departments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                departments.add(mapResultSetToDepartment(resultSet));
            }
        }

        return departments;
    }

    @Override
    public boolean updateDepartment(Department department) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, department.getDepartmentName());
            statement.setString(2, department.getDescription());
            statement.setInt(3, department.getDepartmentId());

            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteDepartment(int departmentId) throws SQLException {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, departmentId);

            return statement.executeUpdate() > 0;
        }
    }

    private Department mapResultSetToDepartment(ResultSet resultSet)
            throws SQLException {

        return new Department(
                resultSet.getInt("department_id"),
                resultSet.getString("department_name"),
                resultSet.getString("description")
        );
    }
}
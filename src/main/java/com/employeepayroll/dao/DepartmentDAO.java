package com.employeepayroll.dao;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.model.Department;

public interface DepartmentDAO {

    void addDepartment(Department department) throws SQLException;

    Department getDepartmentById(int departmentId) throws SQLException;

    List<Department> getAllDepartments() throws SQLException;

    boolean updateDepartment(Department department) throws SQLException;

    boolean deleteDepartment(int departmentId) throws SQLException;
}
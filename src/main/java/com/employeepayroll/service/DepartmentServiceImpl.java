package com.employeepayroll.service;

import java.sql.SQLException;
import java.util.List;

import com.employeepayroll.dao.DepartmentDAO;
import com.employeepayroll.dao.DepartmentDAOImpl;
import com.employeepayroll.model.Department;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    public DepartmentServiceImpl() {
        this.departmentDAO = new DepartmentDAOImpl();
    }

    @Override
    public void addDepartment(Department department) throws SQLException {

        validateDepartment(department);

        departmentDAO.addDepartment(department);
    }

    @Override
    public Department getDepartmentById(int departmentId)
            throws SQLException {

        validateId(departmentId);

        return departmentDAO.getDepartmentById(departmentId);
    }

    @Override
    public List<Department> getAllDepartments() throws SQLException {

        return departmentDAO.getAllDepartments();
    }

    @Override
    public boolean updateDepartment(Department department)
            throws SQLException {

        validateDepartment(department);

        if (department.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }

        return departmentDAO.updateDepartment(department);
    }

    @Override
    public boolean deleteDepartment(int departmentId)
            throws SQLException {

        validateId(departmentId);

        return departmentDAO.deleteDepartment(departmentId);
    }

    private void validateDepartment(Department department) {

        if (department == null) {
            throw new IllegalArgumentException(
                    "Department cannot be null"
            );
        }

        if (department.getDepartmentName() == null ||
                department.getDepartmentName().isBlank()) {

            throw new IllegalArgumentException(
                    "Department name cannot be blank"
            );
        }

        if (department.getDepartmentName().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "Department name cannot exceed 100 characters"
            );
        }
    }

    private void validateId(int departmentId) {

        if (departmentId <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }
    }
}
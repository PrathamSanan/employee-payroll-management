package com.employeepayroll.dao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Department;

class DepartmentDAOImplTest {

    @Test
    void testDepartmentCRUD() throws Exception {

        DepartmentDAO departmentDAO = new DepartmentDAOImpl();

        String departmentName =
                "Test Department " + System.currentTimeMillis();

        Department department =
                new Department(
                        0,
                        departmentName,
                        "Temporary department for DAO testing"
                );

        try {
            // CREATE
            departmentDAO.addDepartment(department);

            assertTrue(
                    department.getDepartmentId() > 0,
                    "Generated department ID should be assigned"
            );

            // READ
            Department savedDepartment =
                    departmentDAO.getDepartmentById(
                            department.getDepartmentId()
                    );

            assertNotNull(savedDepartment);

            assertEquals(
                    departmentName,
                    savedDepartment.getDepartmentName()
            );

            // UPDATE
            department.setDepartmentName(
                    departmentName + " Updated"
            );

            department.setDescription(
                    "Updated department description"
            );

            assertTrue(
                    departmentDAO.updateDepartment(department)
            );

            Department updatedDepartment =
                    departmentDAO.getDepartmentById(
                            department.getDepartmentId()
                    );

            assertNotNull(updatedDepartment);

            assertEquals(
                    departmentName + " Updated",
                    updatedDepartment.getDepartmentName()
            );

            // READ ALL
            List<Department> departments =
                    departmentDAO.getAllDepartments();

            assertFalse(departments.isEmpty());

            // DELETE
            assertTrue(
                    departmentDAO.deleteDepartment(
                            department.getDepartmentId()
                    )
            );

            Department deletedDepartment =
                    departmentDAO.getDepartmentById(
                            department.getDepartmentId()
                    );

            assertNull(deletedDepartment);

        } finally {

            // Cleanup in case a test assertion fails
            departmentDAO.deleteDepartment(
                    department.getDepartmentId()
            );
        }
    }
}

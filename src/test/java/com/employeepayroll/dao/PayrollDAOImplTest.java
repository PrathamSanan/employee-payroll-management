package com.employeepayroll.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Payroll;
import com.employeepayroll.util.DBConnection;

class PayrollDAOImplTest {

    private PayrollDAO createDAO() {
        return new PayrollDAOImpl();
    }

    // --------------------------getExistingEmployeeId---------------------------------------------------

    private int getExistingEmployeeId() throws SQLException {

        String sql = """
                SELECT employee_id
                FROM employees
                ORDER BY employee_id
                LIMIT 1
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("employee_id");
            }
        }

        throw new IllegalStateException(
                "No employee exists in database. Create an employee before running PayrollDAOImplTest."
        );
    }

    // --------------------------createValidPayroll---------------------------------------------------

    private Payroll createValidPayroll() throws SQLException {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(getExistingEmployeeId());

        payroll.setSalaryMonth(
                LocalDate.of(2026, 12, 1)
        );

        payroll.setBasicSalary(
                new BigDecimal("50000.00")
        );

        payroll.setAllowance(
                new BigDecimal("5000.00")
        );

        payroll.setDeduction(
                new BigDecimal("3000.00")
        );

        payroll.setGrossSalary(
                new BigDecimal("55000.00")
        );

        payroll.setNetSalary(
                new BigDecimal("52000.00")
        );

        payroll.setPaymentStatus("PENDING");

        return payroll;
    }

    // --------------------------addPayroll---------------------------------------------------

    @Test
    void testAddPayroll() throws Exception {

        Payroll payroll = createValidPayroll();

        PayrollDAO dao = createDAO();

        assertDoesNotThrow(
                () -> dao.addPayroll(payroll)
        );
    }

    // --------------------------getPayrollById---------------------------------------------------

    @Test
    void testGetPayrollById() throws Exception {

        PayrollDAO dao = createDAO();

        List<Payroll> payrolls = dao.getAllPayrolls();

        if (!payrolls.isEmpty()) {

            int payrollId =
                    payrolls.get(0).getPayrollId();

            Payroll payroll =
                    dao.getPayrollById(payrollId);

            assertNotNull(payroll);
        }
    }

    // --------------------------getAllPayrolls---------------------------------------------------

    @Test
    void testGetAllPayrolls() throws Exception {

        PayrollDAO dao = createDAO();

        List<Payroll> payrollList =
                dao.getAllPayrolls();

        assertNotNull(payrollList);
    }

    // --------------------------getPayrollsByEmployeeId---------------------------------------------------

    @Test
    void testGetPayrollsByEmployeeId() throws Exception {

        PayrollDAO dao = createDAO();

        int employeeId =
                getExistingEmployeeId();

        List<Payroll> payrollList =
                dao.getPayrollsByEmployeeId(employeeId);

        assertNotNull(payrollList);
    }

    // --------------------------updatePayroll---------------------------------------------------

    @Test
    void testUpdatePayroll() throws Exception {

        PayrollDAO dao = createDAO();

        List<Payroll> payrolls =
                dao.getAllPayrolls();

        if (!payrolls.isEmpty()) {

            Payroll payroll =
                    payrolls.get(0);

            payroll.setBasicSalary(
                    new BigDecimal("51000.00")
            );

            payroll.setAllowance(
                    new BigDecimal("6000.00")
            );

            payroll.setDeduction(
                    new BigDecimal("3000.00")
            );

            payroll.setGrossSalary(
                    new BigDecimal("57000.00")
            );

            payroll.setNetSalary(
                    new BigDecimal("54000.00")
            );

            assertDoesNotThrow(
                    () -> dao.updatePayroll(payroll)
            );
        }
    }

    // --------------------------deletePayroll---------------------------------------------------

    @Test
    void testDeletePayroll() throws Exception {

        PayrollDAO dao = createDAO();

        Payroll payroll =
                createValidPayroll();

        /*
         * Use a unique salary month so that this test
         * does not conflict with an existing payroll record.
         */
        payroll.setSalaryMonth(
                LocalDate.of(2099, 1, 1)
        );

        dao.addPayroll(payroll);

        List<Payroll> payrolls =
                dao.getPayrollsByEmployeeId(
                        payroll.getEmployeeId()
                );

        int payrollId = -1;

        for (Payroll savedPayroll : payrolls) {

            if (LocalDate.of(2099, 1, 1)
                    .equals(savedPayroll.getSalaryMonth())) {

                payrollId =
                        savedPayroll.getPayrollId();

                break;
            }
        }

        assertTrue(
                payrollId > 0,
                "Payroll should have been created before delete test"
        );

        assertTrue(
                dao.deletePayroll(payrollId)
        );
    }
}
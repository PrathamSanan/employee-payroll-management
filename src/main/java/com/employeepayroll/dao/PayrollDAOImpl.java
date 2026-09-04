package com.employeepayroll.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.model.Payroll;
import com.employeepayroll.util.DBConnection;

public class PayrollDAOImpl implements PayrollDAO {

    // --------------------------addPayroll---------------------------------------------------

    @Override
    public void addPayroll(Payroll payroll) throws SQLException {

        String sql = """
                INSERT INTO payroll
                (employee_id, salary_month, basic_salary, allowance,
                 deduction, gross_salary, net_salary, payment_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payroll.getEmployeeId());
            statement.setDate(2, Date.valueOf(payroll.getSalaryMonth()));
            statement.setBigDecimal(3, payroll.getBasicSalary());
            statement.setBigDecimal(4, payroll.getAllowance());
            statement.setBigDecimal(5, payroll.getDeduction());
            statement.setBigDecimal(6, payroll.getGrossSalary());
            statement.setBigDecimal(7, payroll.getNetSalary());
            statement.setString(8, payroll.getPaymentStatus());

            statement.executeUpdate();
        }
    }

    // --------------------------getPayrollById---------------------------------------------------

    @Override
    public Payroll getPayrollById(int payrollId) throws SQLException {

        String sql = """
                SELECT payroll_id, employee_id, salary_month,
                       basic_salary, allowance, deduction,
                       gross_salary, net_salary, payment_status,
                       generated_at
                FROM payroll
                WHERE payroll_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payrollId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToPayroll(resultSet);
                }
            }
        }

        return null;
    }

    // --------------------------getAllPayrolls---------------------------------------------------

    @Override
    public List<Payroll> getAllPayrolls() throws SQLException {

        String sql = """
                SELECT payroll_id, employee_id, salary_month,
                       basic_salary, allowance, deduction,
                       gross_salary, net_salary, payment_status,
                       generated_at
                FROM payroll
                ORDER BY payroll_id
                """;

        List<Payroll> payrolls = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                payrolls.add(mapResultSetToPayroll(resultSet));
            }
        }

        return payrolls;
    }

    // --------------------------getPayrollsByEmployeeId---------------------------------------------------

    @Override
    public List<Payroll> getPayrollsByEmployeeId(int employeeId) throws SQLException {

        String sql = """
                SELECT payroll_id, employee_id, salary_month,
                       basic_salary, allowance, deduction,
                       gross_salary, net_salary, payment_status,
                       generated_at
                FROM payroll
                WHERE employee_id = ?
                ORDER BY salary_month DESC
                """;

        List<Payroll> payrolls = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    payrolls.add(mapResultSetToPayroll(resultSet));
                }
            }
        }

        return payrolls;
    }

    // --------------------------updatePayroll---------------------------------------------------

    @Override
    public boolean updatePayroll(Payroll payroll) throws SQLException {

        String sql = """
                UPDATE payroll
                SET employee_id = ?,
                    salary_month = ?,
                    basic_salary = ?,
                    allowance = ?,
                    deduction = ?,
                    gross_salary = ?,
                    net_salary = ?,
                    payment_status = ?
                WHERE payroll_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payroll.getEmployeeId());
            statement.setDate(2, Date.valueOf(payroll.getSalaryMonth()));
            statement.setBigDecimal(3, payroll.getBasicSalary());
            statement.setBigDecimal(4, payroll.getAllowance());
            statement.setBigDecimal(5, payroll.getDeduction());
            statement.setBigDecimal(6, payroll.getGrossSalary());
            statement.setBigDecimal(7, payroll.getNetSalary());
            statement.setString(8, payroll.getPaymentStatus());
            statement.setInt(9, payroll.getPayrollId());

            return statement.executeUpdate() > 0;
        }
    }

    // --------------------------deletePayroll---------------------------------------------------

    @Override
    public boolean deletePayroll(int payrollId) throws SQLException {

        String sql = """
                DELETE FROM payroll
                WHERE payroll_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payrollId);

            return statement.executeUpdate() > 0;
        }
    }

    // --------------------------mapResultSetToPayroll---------------------------------------------------

    private Payroll mapResultSetToPayroll(ResultSet resultSet) throws SQLException {

        Payroll payroll = new Payroll();

        payroll.setPayrollId(resultSet.getInt("payroll_id"));
        payroll.setEmployeeId(resultSet.getInt("employee_id"));

        Date salaryMonth = resultSet.getDate("salary_month");

        if (salaryMonth != null) {
            payroll.setSalaryMonth(salaryMonth.toLocalDate());
        }

        payroll.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
        payroll.setAllowance(resultSet.getBigDecimal("allowance"));
        payroll.setDeduction(resultSet.getBigDecimal("deduction"));
        payroll.setGrossSalary(resultSet.getBigDecimal("gross_salary"));
        payroll.setNetSalary(resultSet.getBigDecimal("net_salary"));
        payroll.setPaymentStatus(resultSet.getString("payment_status"));

        return payroll;
    }
}
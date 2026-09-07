package com.employeepayroll.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.employeepayroll.dao.PayrollDAO;
import com.employeepayroll.model.Payroll;

class PayrollServiceImplTest {

    @Test
    void testGeneratePayrollCalculation() throws Exception {

        PayrollDAO mockDAO = new PayrollDAO() {

            @Override
            public void addPayroll(Payroll payroll) {
            }

            @Override
            public Payroll getPayrollById(int payrollId) {
                return null;
            }

            @Override
            public java.util.List<Payroll> getAllPayrolls() {
                return java.util.List.of();
            }

            @Override
            public java.util.List<Payroll> getPayrollsByEmployeeId(
                    int employeeId) {
                return java.util.List.of();
            }

            @Override
            public boolean updatePayroll(Payroll payroll) {
                return false;
            }

            @Override
            public boolean deletePayroll(int payrollId) {
                return false;
            }
        };

        PayrollService service = new PayrollServiceImpl(mockDAO);

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("3000.00"));

        service.generatePayroll(payroll);

        assertEquals(
                new BigDecimal("55000.00"),
                payroll.getGrossSalary()
        );

        assertEquals(
                new BigDecimal("52000.00"),
                payroll.getNetSalary()
        );

        assertEquals(
                "PENDING",
                payroll.getPaymentStatus()
        );
    }

    @Test
    void testNullPayrollValidation() {

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(null)
        );
    }

    @Test
    void testInvalidEmployeeIdValidation() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(0);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("3000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    @Test
    void testNegativeBasicSalaryValidation() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("-50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("3000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    @Test
    void testNegativeAllowanceValidation() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("-5000.00"));
        payroll.setDeduction(new BigDecimal("3000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    @Test
    void testNegativeDeductionValidation() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("-3000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    @Test
    void testDeductionGreaterThanGrossSalaryValidation() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("60000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    @Test
    void testGetPayrollByIdValidation() {

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getPayrollById(0)
        );
    }

    @Test
    void testGetPayrollsByEmployeeIdValidation() {

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getPayrollsByEmployeeId(0)
        );
    }

    @Test
    void testDeletePayrollValidation() {

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deletePayroll(0)
        );
    }

    @Test
    void testUpdatePayrollValidation() {

        Payroll payroll = new Payroll();

        payroll.setPayrollId(0);
        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(LocalDate.of(2026, 9, 1));
        payroll.setBasicSalary(new BigDecimal("50000.00"));
        payroll.setAllowance(new BigDecimal("5000.00"));
        payroll.setDeduction(new BigDecimal("3000.00"));

        PayrollService service = new PayrollServiceImpl();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updatePayroll(payroll)
        );
    }
}
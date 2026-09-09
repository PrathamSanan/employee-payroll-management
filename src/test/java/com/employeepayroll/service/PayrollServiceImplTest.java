package com.employeepayroll.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.employeepayroll.dao.PayrollDAO;
import com.employeepayroll.model.Payroll;

class PayrollServiceImplTest {

    // --------------------------createPayroll---------------------------------------------------

    private Payroll createPayroll() {

        Payroll payroll = new Payroll();

        payroll.setEmployeeId(1);
        payroll.setSalaryMonth(
                LocalDate.of(2026, 9, 1)
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

        return payroll;
    }

    // --------------------------createMockDAO---------------------------------------------------

    private TestPayrollDAO createMockDAO() {
        return new TestPayrollDAO();
    }

    // --------------------------generatePayroll---------------------------------------------------

    @Test
    void testGeneratePayrollCalculation() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        Payroll payroll = createPayroll();

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

        assertTrue(mockDAO.addPayrollCalled);
    }

    // --------------------------generatePayrollExistingStatus---------------------------------------------------

    @Test
    void testGeneratePayrollKeepsExistingPaymentStatus()
            throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        Payroll payroll = createPayroll();

        payroll.setPaymentStatus("PAID");

        service.generatePayroll(payroll);

        assertEquals(
                "PAID",
                payroll.getPaymentStatus()
        );
    }

    // --------------------------nullPayroll---------------------------------------------------

    @Test
    void testNullPayrollValidation() {

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(null)
        );
    }

    // --------------------------invalidEmployeeId---------------------------------------------------

    @Test
    void testInvalidEmployeeIdValidation() {

        Payroll payroll = createPayroll();

        payroll.setEmployeeId(0);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------nullSalaryMonth---------------------------------------------------

    @Test
    void testNullSalaryMonthValidation() {

        Payroll payroll = createPayroll();

        payroll.setSalaryMonth(null);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------nullBasicSalary---------------------------------------------------

    @Test
    void testNullBasicSalaryValidation() {

        Payroll payroll = createPayroll();

        payroll.setBasicSalary(null);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------negativeBasicSalary---------------------------------------------------

    @Test
    void testNegativeBasicSalaryValidation() {

        Payroll payroll = createPayroll();

        payroll.setBasicSalary(
                new BigDecimal("-50000.00")
        );

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------nullAllowance---------------------------------------------------

    @Test
    void testNullAllowanceValidation() {

        Payroll payroll = createPayroll();

        payroll.setAllowance(null);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------negativeAllowance---------------------------------------------------

    @Test
    void testNegativeAllowanceValidation() {

        Payroll payroll = createPayroll();

        payroll.setAllowance(
                new BigDecimal("-5000.00")
        );

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------nullDeduction---------------------------------------------------

    @Test
    void testNullDeductionValidation() {

        Payroll payroll = createPayroll();

        payroll.setDeduction(null);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------negativeDeduction---------------------------------------------------

    @Test
    void testNegativeDeductionValidation() {

        Payroll payroll = createPayroll();

        payroll.setDeduction(
                new BigDecimal("-3000.00")
        );

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------deductionGreaterThanGross---------------------------------------------------

    @Test
    void testDeductionGreaterThanGrossSalaryValidation() {

        Payroll payroll = createPayroll();

        payroll.setDeduction(
                new BigDecimal("60000.00")
        );

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generatePayroll(payroll)
        );
    }

    // --------------------------getPayrollByIdValidation---------------------------------------------------

    @Test
    void testGetPayrollByIdValidation() {

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getPayrollById(0)
        );
    }

    // --------------------------getPayrollById---------------------------------------------------

    @Test
    void testGetPayrollById() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        Payroll expectedPayroll = createPayroll();

        expectedPayroll.setPayrollId(10);

        mockDAO.payroll = expectedPayroll;

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        Payroll actualPayroll =
                service.getPayrollById(10);

        assertNotNull(actualPayroll);

        assertEquals(
                10,
                actualPayroll.getPayrollId()
        );

        assertTrue(
                mockDAO.getPayrollByIdCalled
        );

        assertEquals(
                10,
                mockDAO.requestedPayrollId
        );
    }

    // --------------------------getAllPayrolls---------------------------------------------------

    @Test
    void testGetAllPayrolls() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        mockDAO.payrolls.add(createPayroll());

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        List<Payroll> result =
                service.getAllPayrolls();

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );

        assertTrue(
                mockDAO.getAllPayrollsCalled
        );
    }

    // --------------------------getPayrollsByEmployeeIdValidation---------------------------------------------------

    @Test
    void testGetPayrollsByEmployeeIdValidation() {

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getPayrollsByEmployeeId(0)
        );
    }

    // --------------------------getPayrollsByEmployeeId---------------------------------------------------

    @Test
    void testGetPayrollsByEmployeeId() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        Payroll payroll = createPayroll();

        mockDAO.payrolls.add(payroll);

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        List<Payroll> result =
                service.getPayrollsByEmployeeId(1);

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );

        assertTrue(
                mockDAO.getPayrollsByEmployeeIdCalled
        );

        assertEquals(
                1,
                mockDAO.requestedEmployeeId
        );
    }

    // --------------------------updatePayrollValidation---------------------------------------------------

    @Test
    void testUpdatePayrollValidation() {

        Payroll payroll = createPayroll();

        payroll.setPayrollId(0);

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updatePayroll(payroll)
        );
    }

    // --------------------------updatePayroll---------------------------------------------------

    @Test
    void testUpdatePayroll() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        Payroll payroll = createPayroll();

        payroll.setPayrollId(10);

        payroll.setPaymentStatus("PENDING");

        boolean result =
                service.updatePayroll(payroll);

        assertTrue(result);

        assertTrue(
                mockDAO.updatePayrollCalled
        );

        assertEquals(
                new BigDecimal("55000.00"),
                payroll.getGrossSalary()
        );

        assertEquals(
                new BigDecimal("52000.00"),
                payroll.getNetSalary()
        );
    }

    // --------------------------deletePayrollValidation---------------------------------------------------

    @Test
    void testDeletePayrollValidation() {

        PayrollService service =
                new PayrollServiceImpl(createMockDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deletePayroll(0)
        );
    }

    // --------------------------deletePayroll---------------------------------------------------

    @Test
    void testDeletePayroll() throws Exception {

        TestPayrollDAO mockDAO = createMockDAO();

        PayrollService service =
                new PayrollServiceImpl(mockDAO);

        boolean result =
                service.deletePayroll(10);

        assertTrue(result);

        assertTrue(
                mockDAO.deletePayrollCalled
        );

        assertEquals(
                10,
                mockDAO.requestedPayrollId
        );
    }

    // --------------------------TestPayrollDAO---------------------------------------------------

    private static class TestPayrollDAO implements PayrollDAO {

        private boolean addPayrollCalled;
        private boolean getPayrollByIdCalled;
        private boolean getAllPayrollsCalled;
        private boolean getPayrollsByEmployeeIdCalled;
        private boolean updatePayrollCalled;
        private boolean deletePayrollCalled;

        private int requestedPayrollId;
        private int requestedEmployeeId;

        private Payroll payroll;

        private final List<Payroll> payrolls =
                new ArrayList<>();

        @Override
        public void addPayroll(Payroll payroll)
                throws SQLException {

            addPayrollCalled = true;
            this.payroll = payroll;
        }

        @Override
        public Payroll getPayrollById(int payrollId)
                throws SQLException {

            getPayrollByIdCalled = true;
            requestedPayrollId = payrollId;

            return payroll;
        }

        @Override
        public List<Payroll> getAllPayrolls()
                throws SQLException {

            getAllPayrollsCalled = true;

            return payrolls;
        }

        @Override
        public List<Payroll> getPayrollsByEmployeeId(
                int employeeId)
                throws SQLException {

            getPayrollsByEmployeeIdCalled = true;
            requestedEmployeeId = employeeId;

            return payrolls;
        }

        @Override
        public boolean updatePayroll(Payroll payroll)
                throws SQLException {

            updatePayrollCalled = true;
            this.payroll = payroll;

            return true;
        }

        @Override
        public boolean deletePayroll(int payrollId)
                throws SQLException {

            deletePayrollCalled = true;
            requestedPayrollId = payrollId;

            return true;
        }
    }
}
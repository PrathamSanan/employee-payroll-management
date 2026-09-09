package com.employeepayroll.service;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Attendance;

class AttendanceServiceImplTest {

    private AttendanceService createService() {
        return new AttendanceServiceImpl();
    }

    private Attendance createValidAttendance() {

        Attendance attendance = new Attendance();

        attendance.setEmployeeId(14);
        attendance.setAttendanceDate(
        LocalDate.now().minusDays(
                (System.nanoTime() % 100000) + 1)
        );
        attendance.setStatus("PRESENT");
        attendance.setCheckIn(
                LocalTime.of(9, 0)
        );
        attendance.setCheckOut(
                LocalTime.of(18, 0)
        );

        return attendance;
    }

    // --------------------------markAttendance---------------------------------------------------

    @Test
    void testMarkValidAttendance() {

        Attendance attendance = createValidAttendance();

        AttendanceService service = createService();

        assertDoesNotThrow(
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testMarkNullAttendance() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(null)
        );
    }

    @Test
    void testInvalidEmployeeId() {

        Attendance attendance = createValidAttendance();
        attendance.setEmployeeId(0);

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testNullAttendanceDate() {

        Attendance attendance = createValidAttendance();
        attendance.setAttendanceDate(null);

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testFutureAttendanceDate() {

        Attendance attendance = createValidAttendance();
        attendance.setAttendanceDate(
                LocalDate.now().plusDays(1)
        );

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testInvalidAttendanceStatus() {

        Attendance attendance = createValidAttendance();
        attendance.setStatus("INVALID");

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testPresentWithoutCheckIn() {

        Attendance attendance = createValidAttendance();
        attendance.setCheckIn(null);

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testCheckoutBeforeCheckin() {

        Attendance attendance = createValidAttendance();

        attendance.setCheckIn(
                LocalTime.of(18, 0)
        );

        attendance.setCheckOut(
                LocalTime.of(9, 0)
        );

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testAbsentWithCheckIn() {

        Attendance attendance = createValidAttendance();

        attendance.setStatus("ABSENT");
        attendance.setCheckIn(null);
        attendance.setCheckOut(null);

        AttendanceService service = createService();

        assertDoesNotThrow(
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testAbsentWithCheckInTime() {

        Attendance attendance = createValidAttendance();

        attendance.setStatus("ABSENT");

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    @Test
    void testLeaveWithCheckInTime() {

        Attendance attendance = createValidAttendance();

        attendance.setStatus("LEAVE");

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addAttendance(attendance)
        );
    }

    // --------------------------getAttendanceById---------------------------------------------------

    @Test
    void testGetAttendanceWithInvalidId() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceById(0)
        );
    }

    @Test
    void testGetAttendanceWithNegativeId() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceById(-1)
        );
    }

    // --------------------------getAttendanceByEmployee---------------------------------------------------

    @Test
    void testGetAttendanceWithInvalidEmployeeId() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceByEmployeeId(
                        0,
                        LocalDate.now().minusDays(10),
                        LocalDate.now()
                )
        );
    }

    @Test
    void testGetAttendanceWithNullStartDate() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceByEmployeeId(
                        14,
                        null,
                        LocalDate.now()
                )
        );
    }

    @Test
    void testGetAttendanceWithNullEndDate() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceByEmployeeId(
                        14,
                        LocalDate.now().minusDays(10),
                        null
                )
        );
    }

    @Test
    void testInvalidDateRange() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceByEmployeeId(
                        14,
                        LocalDate.now(),
                        LocalDate.now().minusDays(10)
                )
        );
    }

    // --------------------------getAttendanceByDate---------------------------------------------------

    @Test
    void testGetAttendanceWithNullDate() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getAttendanceByDate(null)
        );
    }

    // --------------------------updateAttendance---------------------------------------------------

    @Test
    void testUpdateNullAttendance() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateAttendance(null)
        );
    }

    @Test
    void testUpdateWithInvalidId() {

        Attendance attendance = createValidAttendance();
        attendance.setAttendanceId(0);

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateAttendance(attendance)
        );
    }

    // --------------------------deleteAttendance---------------------------------------------------

    @Test
    void testDeleteWithInvalidId() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteAttendance(0)
        );
    }

    @Test
    void testDeleteWithNegativeId() {

        AttendanceService service = createService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteAttendance(-1)
        );
    }
}
package com.employeepayroll.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.employeepayroll.model.Attendance;

class AttendanceDAOImplTest {

    private AttendanceDAO createDAO() {
        return new AttendanceDAOImpl();
    }

    private Attendance createValidAttendance() {

        Attendance attendance = new Attendance();

        attendance.setEmployeeId(14);
        attendance.setAttendanceDate(
        LocalDate.now().minusDays(
                (System.nanoTime() % 100000) + 1
            )
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

    // --------------------------addAttendance---------------------------------------------------

    @Test
    void testAddAttendance() {

        Attendance attendance = createValidAttendance();

        AttendanceDAO dao = createDAO();

        assertDoesNotThrow(
                () -> dao.addAttendance(attendance)
        );
    }

    // --------------------------getAttendanceById---------------------------------------------------

    @Test
    void testGetAttendanceById() {

        AttendanceDAO dao = createDAO();

        assertDoesNotThrow(
                () -> dao.getAttendanceById(1)
        );
    }

    // --------------------------getAttendanceByEmployeeId---------------------------------------------------

    @Test
    void testGetAttendanceByEmployeeId() {

        AttendanceDAO dao = createDAO();

        List<Attendance> attendanceList =
                dao.getAttendanceByEmployeeId(14);

        assertNotNull(attendanceList);
    }

    // --------------------------getAttendanceByEmployee---------------------------------------------------

    @Test
    void testGetAttendanceByEmployee() {

        AttendanceDAO dao = createDAO();

        List<Attendance> attendanceList =
                dao.getAttendanceByEmployeeId(14);

        assertNotNull(attendanceList);
    }

    // --------------------------getAttendanceByDate---------------------------------------------------

    @Test
    void testGetAttendanceByDate() {

        AttendanceDAO dao = createDAO();

        List<Attendance> attendanceList =
                dao.getAttendanceByDate(
                        LocalDate.now()
                );

        assertNotNull(attendanceList);
    }

    // --------------------------updateAttendance---------------------------------------------------

    @Test
    void testUpdateAttendance() {

        Attendance attendance = createValidAttendance();

        attendance.setAttendanceId(1);

        AttendanceDAO dao = createDAO();

        assertDoesNotThrow(
                () -> dao.updateAttendance(attendance)
        );
    }

    // --------------------------deleteAttendance---------------------------------------------------

    @Test
    void testDeleteAttendance() {

        AttendanceDAO dao = createDAO();

        assertDoesNotThrow(
                () -> dao.deleteAttendance(1)
        );
    }
}
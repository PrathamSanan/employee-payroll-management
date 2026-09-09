package com.employeepayroll.dao;

import java.time.LocalDate;
import java.util.List;

import com.employeepayroll.model.Attendance;

public interface AttendanceDAO {

    void addAttendance(Attendance attendance);

    Attendance getAttendanceById(int attendanceId);

    List<Attendance> getAttendanceByEmployeeId(int employeeId);

    List<Attendance> getAttendanceByDate(LocalDate attendanceDate);

    void updateAttendance(Attendance attendance);

    void deleteAttendance(int attendanceId);
}
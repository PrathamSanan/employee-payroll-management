package com.employeepayroll.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.model.Attendance;
import com.employeepayroll.util.DBConnection;

public class AttendanceDAOImpl implements AttendanceDAO {

    // --------------------------addAttendance---------------------------------------------------
    @Override
    public void addAttendance(Attendance attendance) {

        String sql = """
                INSERT INTO attendance
                (employee_id, attendance_date, status, check_in, check_out)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                )
        ) {

            statement.setInt(1, attendance.getEmployeeId());
            statement.setDate(
                    2,
                    Date.valueOf(attendance.getAttendanceDate())
            );
            statement.setString(3, attendance.getStatus());

            if (attendance.getCheckIn() != null) {
                statement.setTime(
                        4,
                        Time.valueOf(attendance.getCheckIn())
                );
            } else {
                statement.setNull(
                        4,
                        java.sql.Types.TIME
                );
            }

            if (attendance.getCheckOut() != null) {
                statement.setTime(
                        5,
                        Time.valueOf(attendance.getCheckOut())
                );
            } else {
                statement.setNull(
                        5,
                        java.sql.Types.TIME
                );
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    attendance.setAttendanceId(
                            generatedKeys.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to add attendance",
                    e
            );
        }
    }

    // --------------------------getAttendanceById---------------------------------------------------

    @Override
    public Attendance getAttendanceById(int attendanceId) {

        String sql = """
                SELECT attendance_id,
                       employee_id,
                       attendance_date,
                       status,
                       check_in,
                       check_out
                FROM attendance
                WHERE attendance_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, attendanceId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSetToAttendance(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get attendance", e);
        }
    }

    // --------------------------getAttendanceByEmployeeId---------------------------------------------------

    @Override
    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {

        String sql = """
                SELECT attendance_id,
                       employee_id,
                       attendance_date,
                       status,
                       check_in,
                       check_out
                FROM attendance
                WHERE employee_id = ?
                ORDER BY attendance_date DESC
                """;

        List<Attendance> attendanceList = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    attendanceList.add(mapResultSetToAttendance(resultSet));
                }
            }

            return attendanceList;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to get attendance for employee",
                    e
            );
        }
    }

    // --------------------------getAttendanceByDate---------------------------------------------------

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate attendanceDate) {

        String sql = """
                SELECT attendance_id,
                       employee_id,
                       attendance_date,
                       status,
                       check_in,
                       check_out
                FROM attendance
                WHERE attendance_date = ?
                ORDER BY employee_id
                """;

        List<Attendance> attendanceList = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setDate(1, Date.valueOf(attendanceDate));

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    attendanceList.add(mapResultSetToAttendance(resultSet));
                }
            }

            return attendanceList;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to get attendance by date",
                    e
            );
        }
    }

    // --------------------------updateAttendance---------------------------------------------------

    @Override
    public void updateAttendance(Attendance attendance) {

        String sql = """
                UPDATE attendance
                SET employee_id = ?,
                    attendance_date = ?,
                    status = ?,
                    check_in = ?,
                    check_out = ?
                WHERE attendance_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, attendance.getEmployeeId());
            statement.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            statement.setString(3, attendance.getStatus());

            if (attendance.getCheckIn() != null) {
                statement.setTime(4, Time.valueOf(attendance.getCheckIn()));
            } else {
                statement.setNull(4, java.sql.Types.TIME);
            }

            if (attendance.getCheckOut() != null) {
                statement.setTime(5, Time.valueOf(attendance.getCheckOut()));
            } else {
                statement.setNull(5, java.sql.Types.TIME);
            }

            statement.setInt(6, attendance.getAttendanceId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update attendance", e);
        }
    }

    // --------------------------deleteAttendance---------------------------------------------------

    @Override
    public void deleteAttendance(int attendanceId) {

        String sql = """
                DELETE FROM attendance
                WHERE attendance_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, attendanceId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete attendance", e);
        }
    }

    // --------------------------mapResultSetToAttendance---------------------------------------------------

    private Attendance mapResultSetToAttendance(ResultSet resultSet)
            throws SQLException {

        Attendance attendance = new Attendance();

        attendance.setAttendanceId(
                resultSet.getInt("attendance_id")
        );

        attendance.setEmployeeId(
                resultSet.getInt("employee_id")
        );

        attendance.setAttendanceDate(
                resultSet.getDate("attendance_date").toLocalDate()
        );

        attendance.setStatus(
                resultSet.getString("status")
        );

        Time checkIn = resultSet.getTime("check_in");

        if (checkIn != null) {
            attendance.setCheckIn(checkIn.toLocalTime());
        }

        Time checkOut = resultSet.getTime("check_out");

        if (checkOut != null) {
            attendance.setCheckOut(checkOut.toLocalTime());
        }

        return attendance;
    }
}
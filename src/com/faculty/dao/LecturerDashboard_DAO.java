package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.model.Lecturer;
import com.faculty.utils.DBConnection;

public class LecturerDashboard_DAO {
    private Connection conn;
    private String lastError = "";

    public LecturerDashboard_DAO() {
        conn = DBConnection.getConnection();
    }

    public String getLastError() {
        return lastError;
    }

    public Lecturer getLecturerByEmail(String email) {
        if (conn == null) {
            lastError = "Database connection unavailable.";
            return null;
        }
        Lecturer lecturer = null;
        String query = "SELECT * FROM lecturers WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String coursesStr = rs.getString("courses_teaching");
                    String[] courses = coursesStr != null ? coursesStr.split(",") : new String[0];
                    lecturer = new Lecturer(
                            rs.getString("full_name"),
                            rs.getString("lecturer_id"),
                            rs.getString("department"),
                            courses,
                            rs.getString("email"),
                            rs.getString("mobile"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
        }
        return lecturer;
    }

    public boolean updateLecturer(String originalEmail, Lecturer lecturer) {
        if (conn == null)
            return false;
        String query = "UPDATE lecturers SET full_name=?, lecturer_id=?, department=?, courses_teaching=?, mobile=?, email=? WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getLecturerId());
            ps.setString(3, lecturer.getDepartment());
            ps.setString(4, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(5, lecturer.getMobile());
            ps.setString(6, lecturer.getEmail());
            ps.setString(7, originalEmail);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean addLecturer(Lecturer lecturer) {
        if (conn == null)
            return false;
        String query = "INSERT INTO lecturers (full_name, lecturer_id, department, courses_teaching, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getLecturerId());
            ps.setString(3, lecturer.getDepartment());
            ps.setString(4, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(5, lecturer.getEmail());
            ps.setString(6, lecturer.getMobile());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public List<String> getAllDepartmentNames() {
        List<String> departments = new ArrayList<>();
        if (conn == null)
            return departments;
        String query = "SELECT department_name FROM departments";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(rs.getString("department_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
        }
        return departments;
    }
}

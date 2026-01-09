package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.faculty.model.Student;
import com.faculty.utils.DBConnection;

public class StudentDashboard_DAO {
    private Connection conn;
    private String lastError = "";

    public StudentDashboard_DAO() {
        conn = DBConnection.getConnection();
    }

    public String getLastError() {
        return lastError;
    }

    public Student getStudentByEmail(String email) {
        Student student = null;
        String query = "SELECT * FROM students WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student(
                            rs.getString("full_name"),
                            rs.getString("student_id"),
                            rs.getString("degree"),
                            rs.getString("email"),
                            rs.getString("mobile"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
        }
        return student;
    }

    public boolean updateStudent(String originalEmail, Student student) {
        String query = "UPDATE students SET full_name=?, degree=?, mobile=?, student_id=?, email=? WHERE email=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, student.getFullName());
            ps.setString(2, student.getDegree());
            ps.setString(3, student.getMobile());
            ps.setString(4, student.getStudentId());
            ps.setString(5, student.getEmail());
            ps.setString(6, originalEmail);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (full_name, student_id, degree, email, mobile) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, student.getFullName());
            ps.setString(2, student.getStudentId());
            ps.setString(3, student.getDegree());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getMobile());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }
}

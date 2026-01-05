package com.faculty.dao;

import com.faculty.model.User;
import com.faculty.util.DBConnection;

import java.sql.*;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        conn = DBConnection.getConnection();
    }

    // Save user if not exists (used in Sign In)
    public boolean register(User user) {
        try {
            String checkQuery = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkQuery);
            psCheck.setString(1, user.getUsername());
            psCheck.setString(2, user.getPassword());
            psCheck.setString(3, user.getRole());
            ResultSet rs = psCheck.executeQuery();

            if(rs.next()) {
                return false; // user already exists
            }

            String insertQuery = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if user exists for Sign Up
    public boolean checkUserExists(String username, String password, String role) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

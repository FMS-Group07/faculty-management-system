package com.faculty.dao;

import com.faculty.model.User;
import com.faculty.utils.DBConnection;

import java.sql.*;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        conn = DBConnection.getConnection();
    }

    // Save user if not exists (used in Sign Up)
    // Returns: 0 = Success, 1 = User Exists, 2 = Database Error
    public int register(User user) {
        if (conn == null) {
            System.err.println("❌ Database connection is null!");
            return 2;
        }
        try {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkQuery);
            psCheck.setString(1, user.getUsername());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                return 1; // user already exists
            }

            String insertQuery = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.executeUpdate();
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        }
    }

    // Check if user exists for Sign In
    public boolean checkUserExists(String username, String password, String role) {
        if (conn == null)
            return false;
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

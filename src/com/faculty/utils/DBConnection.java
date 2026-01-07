package com.faculty.utils;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/faculty_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if(conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

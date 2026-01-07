package com.faculty.utils;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://student-mysql-small.mysql.database.azure.com:3306/faculty_db?useSSL=true&requireSSL=false";
    private static final String USER = "dbadmin";
    private static final String PASSWORD = "Faculty@2026!";

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

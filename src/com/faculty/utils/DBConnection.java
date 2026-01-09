package com.faculty.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Azure MySQL details
    private static final String HOST = "student-mysql-small.mysql.database.azure.com";
    private static final String DATABASE = "faculty_db"; // make sure this DB exists
    private static final String USER = "dbadmin";
    private static final String PASSWORD = "Faculty@2026!"; // use real password

    private static final String URL =
            "jdbc:mysql://" + HOST + ":3306/" + DATABASE +
                    "?useSSL=true&requireSSL=true&allowPublicKeyRetrieval=true";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to Azure MySQL successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Azure MySQL connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
}

package com.faculty.utils;

import java.sql.*;

public class DBProbe {
    public static void main(String[] args) {
        System.out.println("--- DB Probe Start ---");
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Connection Failed.");
                return;
            }
            DatabaseMetaData md = conn.getMetaData();
            System.out.println("Table: lecturers currently has columns:");

            try (ResultSet rs = md.getColumns(null, null, "lecturers", null)) {
                while (rs.next()) {
                    System.out.println(" - " + rs.getString("COLUMN_NAME") + " (" + rs.getString("TYPE_NAME") + ")");
                }
            }

            System.out.println("\nSample Data (Top 1):");
            try (Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM lecturers LIMIT 1")) {
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rs.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        System.out.println(rsmd.getColumnName(i) + ": " + rs.getString(i));
                    }
                } else {
                    System.out.println("No data found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--- DB Probe End ---");
    }
}

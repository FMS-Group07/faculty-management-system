package com.faculty.utils;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection SUCCESSFUL!");
        } else {
            System.out.println("Connection FAILED!");
        }
    }
}

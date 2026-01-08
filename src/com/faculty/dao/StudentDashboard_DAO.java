package com.faculty.dao;

public class StudentDashboard_DAO {

    public String[] getProfileData() {
        return new String[] {
                "Kumar Sangakkara",
                "ET/2022/011",
                "Engineering Technology",
                "kumarsa-et22011@stu.kln.ac.lk",
                "0123456789"
        };
    }

    public Object[][] getEnrolledCourses() {
        return new Object[][] {
                {"ETEC 21062", "OOP", 2, "A+"},
                {"ETEC 21052", "OOP", 2, "B"},
                {"ETEC 21042", "OOP", 2, "A"},
                {"ETEC 21032", "OOP", 2, "D"},
                {"ETEC 21022", "OOP", 2, "C"},
                {"ETEC 21012", "OOP", 2, "B"}
        };
    }

    // --- Time Table Data ---

    public String[] getTimeTableColumns() {
        return new String[] {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    }

    public String[][] getMorningData() {
        return new String[][] {
                {"08.00", "OOP", "OOP", "OOP", "OOP", "OOP"},
                {"10.00", "OOP", "OOP", "OOP", "OOP", "OOP"}
        };
    }

    public String[][] getAfternoonData() {
        return new String[][] {
                {"01.00", "SE", "OOP", "SE", "SE", "SE"},
                {"03.00", "SE", "OOP", "SE", "SE", "SE"}
        };
    }
}
package com.faculty.dao;

public class LecturerDashboard_DAO {

    public Object[][] getTeachingCourses() {
        return new Object[][] {
                {"SE 21062", "OOP", 2, "60"},
                {"OOP1 21052", "OOP", 2, "80"},
                {"OOP2 21042", "OOP", 2, "85"},
                {"SE 21032", "OOP", 2, "50"},
                {"OOP 21022", "OOP", 2, "80"},
                {"OOP 21012", "OOP", 2, "70"}
        };
    }

    public String[] getCourseColumns() {
        return new String[] {"Course Code", "Course Name", "Credits", "Total Students"};
    }

    // Simulating fetching profile data
    public String[] getProfileData() {
        return new String[] {
                "Lucky Dias",
                "FCT_001",
                "Computer Systems Engineering",
                "luckydias@le.kln.ac.lk",
                "0716656789"
        };
    }
}
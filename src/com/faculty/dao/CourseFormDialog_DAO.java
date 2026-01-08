package com.faculty.dao;

public class CourseFormDialog_DAO {

    /**
     * Validates if the course input is valid.
     * @return true if valid, false otherwise (simulated)
     */
    public boolean validateCourseData(String code, String name, String credits) {
        if (code == null || code.trim().isEmpty()) return false;
        if (name == null || name.trim().isEmpty()) return false;

        // Example: Validate credits is a number
        try {
            int c = Integer.parseInt(credits);
            if (c < 0 || c > 20) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
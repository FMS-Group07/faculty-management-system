package com.faculty.dao;

public class LecturerFormDialog_DAO {

    /**
     * Validates input for adding/editing a lecturer.
     * @return true if valid, false otherwise.
     */
    public boolean validateLecturerData(String name, String dept, String courses, String email, String mobile) {
        if (name == null || name.trim().isEmpty()) return false;
        if (dept == null || dept.trim().isEmpty()) return false;
        if (courses == null || courses.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;

        // Basic Mobile Validation (Example: Must be digits)
        if (mobile == null || !mobile.matches("\\d+")) {
            return false;
        }

        return true;
    }
}
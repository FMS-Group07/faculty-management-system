package com.faculty.dao;

public class StudentFormDialog_DAO {

    /**
     * Validates input for adding/editing a student.
     * @return true if valid, false otherwise.
     */
    public boolean validateStudentData(String name, String id, String degree, String email, String mobile) {
        if (name == null || name.trim().isEmpty()) return false;
        if (id == null || id.trim().isEmpty()) return false;
        if (degree == null || degree.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;

        // Basic Mobile validation (must be digits)
        if (mobile == null || !mobile.matches("\\d+")) {
            return false;
        }

        return true;
    }
}
package com.faculty.dao;

public class DegreeFormDialog_DAO {

    /**
     * Validates input for adding/editing a degree.
     * @return true if valid, false otherwise.
     */
    public boolean validateDegreeData(String name, String department, String students) {
        if (name == null || name.trim().isEmpty()) return false;
        if (department == null || department.trim().isEmpty()) return false;

        // Validate that 'students' is a valid number
        try {
            int s = Integer.parseInt(students);
            if (s < 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
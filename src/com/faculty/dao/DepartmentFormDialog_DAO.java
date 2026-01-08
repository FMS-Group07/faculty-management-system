package com.faculty.dao;

public class DepartmentFormDialog_DAO {

    /**
     * Validates input for adding/editing a department.
     * @return true if valid, false otherwise.
     */
    public boolean validateDepartmentData(String name, String hod, String staffCount) {
        if (name == null || name.trim().isEmpty()) return false;
        if (hod == null || hod.trim().isEmpty()) return false;

        // Validate that 'No of Staff' is a number
        try {
            int count = Integer.parseInt(staffCount);
            if (count < 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
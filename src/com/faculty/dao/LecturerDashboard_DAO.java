package com.faculty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.faculty.model.Lecturer;
import com.faculty.utils.DBConnection;

public class LecturerDashboard_DAO {
    private Connection conn;
    private String lastError = "";
    private Boolean useDepartmentIds = null;

    public LecturerDashboard_DAO() {
        conn = DBConnection.getConnection();
    }

    private void checkSchema() {
        if (conn == null)
            return;
        if (useDepartmentIds != null)
            return;

        // Default to safe assumption (Names) unless proven otherwise
        useDepartmentIds = false;

        try {
            java.sql.DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getImportedKeys(conn.getCatalog(), null, "lecturers")) {
                while (rs.next()) {
                    String fkColumn = rs.getString("FKCOLUMN_NAME");
                    String pkColumn = rs.getString("PKCOLUMN_NAME");
                    String pkTable = rs.getString("PKTABLE_NAME");

                    if ("department".equalsIgnoreCase(fkColumn) && "departments".equalsIgnoreCase(pkTable)) {
                        System.out.println("DAO: FK Detected on 'department' -> " + pkTable + "." + pkColumn);
                        if ("department_id".equalsIgnoreCase(pkColumn)) {
                            useDepartmentIds = true;
                        } else {
                            useDepartmentIds = false;
                        }
                        return; // Found the mapping, stop looking
                    }
                }
            }
            // If no FK found, check if department_id exists to be safe?
            // Better to stick to names as AdminDAO uses names.
            System.out.println("DAO: No explicit FK on 'department' found. Using default (Names).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDepartmentIdByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        if (Boolean.TRUE.equals(useDepartmentIds)) {
            String query = "SELECT department_id FROM departments WHERE department_name = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getString("department_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private String getDepartmentNameById(String id) {
        if (id == null || id.isEmpty())
            return "";
        if (Boolean.TRUE.equals(useDepartmentIds)) {
            String query = "SELECT department_name FROM departments WHERE department_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getString("department_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public Lecturer getLecturerByEmail(String email) {
        if (conn == null) {
            lastError = "Database connection unavailable.";
            return null;
        }
        checkSchema();

        Lecturer lecturer = null;
        String query = "SELECT * FROM lecturers WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String coursesStr = rs.getString("courses_teaching");
                    String[] courses = coursesStr != null ? coursesStr.split(",") : new String[0];

                    String lId = "";
                    lId = rs.getString("salary_id");

                    // Resolve Department Name
                    String rawDept = rs.getString("department");
                    String deptName = Boolean.TRUE.equals(useDepartmentIds) ? getDepartmentNameById(rawDept) : rawDept;

                    lecturer = new Lecturer(
                            rs.getString("full_name"),
                            lId,
                            deptName,
                            courses,
                            rs.getString("email"),
                            rs.getString("mobile"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
        }
        return lecturer;
    }

    public boolean updateLecturer(String originalEmail, Lecturer lecturer) {
        if (conn == null)
            return false;
        checkSchema();

        // Resolve Department Value (ID or Name)
        String deptValue = getDepartmentIdByName(lecturer.getDepartment());

        String query = "UPDATE lecturers SET full_name=?, salary_id=?, department=?, courses_teaching=?, mobile=?, email=? WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getLecturerId());
            ps.setString(3, deptValue);
            ps.setString(4, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(5, lecturer.getMobile());
            ps.setString(6, lecturer.getEmail());
            ps.setString(7, originalEmail);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && (msg.contains("Unknown column") || msg.contains("salary_id"))) {
                // Fallback to legacy update
                System.out.println("DAO: Fallback - Updating without salary_id");
                String fallbackQuery = "UPDATE lecturers SET full_name=?, department=?, courses_teaching=?, mobile=?, email=? WHERE email=?";
                try (PreparedStatement ps = conn.prepareStatement(fallbackQuery)) {
                    ps.setString(1, lecturer.getFullName());
                    ps.setString(2, deptValue);
                    ps.setString(3, String.join(",", lecturer.getCoursesTeaching()));
                    ps.setString(4, lecturer.getMobile());
                    ps.setString(5, lecturer.getEmail());
                    ps.setString(6, originalEmail);
                    return ps.executeUpdate() > 0;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    lastError = "Fallback Update Failed: " + ex.getMessage() + "\nDEBUG: " + probeSchema();
                    return false;
                }
            }
            e.printStackTrace();
            lastError = e.getMessage() + "\nDEBUG: " + probeSchema();
            return false;
        }
    }

    public boolean addLecturer(Lecturer lecturer) {
        if (conn == null)
            return false;
        checkSchema();

        // Resolve Department Value (ID or Name)
        String deptValue = getDepartmentIdByName(lecturer.getDepartment());

        String query = "INSERT INTO lecturers (full_name, salary_id, department, courses_teaching, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getLecturerId());
            ps.setString(3, deptValue);
            ps.setString(4, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(5, lecturer.getEmail());
            ps.setString(6, lecturer.getMobile());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && (msg.contains("Unknown column") || msg.contains("salary_id"))) {
                // Fallback to legacy insert
                System.out.println("DAO: Fallback - Inserting without salary_id");
                String fallbackQuery = "INSERT INTO lecturers (full_name, department, courses_teaching, email, mobile) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(fallbackQuery)) {
                    ps.setString(1, lecturer.getFullName());
                    ps.setString(2, deptValue);
                    ps.setString(3, String.join(",", lecturer.getCoursesTeaching()));
                    ps.setString(4, lecturer.getEmail());
                    ps.setString(5, lecturer.getMobile());
                    return ps.executeUpdate() > 0;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    lastError = "Fallback Insert Failed: " + ex.getMessage();
                    return false;
                }
            }
            e.printStackTrace();
            lastError = e.getMessage() + "\nDEBUG: " + probeSchema();
            return false;
        }
    }

    public List<String> getAllDepartmentNames() {
        List<String> departments = new ArrayList<>();
        if (conn == null)
            return departments;
        String query = "SELECT department_name FROM departments";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(rs.getString("department_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
        }
        return departments;
    }

    public String getLastError() {
        return lastError;
    }

    private String probeSchema() {
        StringBuilder sb = new StringBuilder();
        try {
            java.sql.DatabaseMetaData md = conn.getMetaData();
            // Check FK
            sb.append("[FK Check] ");
            try (ResultSet rs = md.getImportedKeys(conn.getCatalog(), null, "lecturers")) {
                boolean found = false;
                while (rs.next()) {
                    if ("department".equalsIgnoreCase(rs.getString("FKCOLUMN_NAME"))) {
                        sb.append("Found FK on 'department' -> ").append(rs.getString("PKTABLE_NAME")).append(".")
                                .append(rs.getString("PKCOLUMN_NAME"));
                        found = true;
                    }
                }
                if (!found)
                    sb.append("No FK on 'department'. ");
            }

            // Check Departments Table
            sb.append(" [Depts Table] ");
            try (java.sql.Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM departments LIMIT 1")) {
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    sb.append(rsmd.getColumnName(i)).append("(").append(rsmd.getColumnTypeName(i)).append(") ");
                }
            }

            // Check Lecturers Table
            sb.append(" [Lecturers Table] ");
            try (java.sql.Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM lecturers LIMIT 1")) {
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if ("department".equalsIgnoreCase(rsmd.getColumnName(i))) {
                        sb.append("department (").append(rsmd.getColumnTypeName(i)).append(") ");
                    }
                }
            }

        } catch (Exception e) {
            sb.append("Probe Error: ").append(e.getMessage());
        }
        return sb.toString();
    }
}

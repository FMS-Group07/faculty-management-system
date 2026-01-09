package com.faculty.dao;

import com.faculty.model.*;
import com.faculty.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardView_DAO {

    private Connection conn;

    public AdminDashboardView_DAO() {
        conn = DBConnection.getConnection();
    }

    private String lastError = "";

    public String getLastError() {
        return lastError;
    }

    // ================= STUDENT OPERATIONS =================

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        if (conn == null)
            return students; // Return empty list if no connection
        String query = "SELECT * FROM students";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("full_name"),
                        rs.getString("student_id"),
                        rs.getString("degree"),
                        rs.getString("email"),
                        rs.getString("mobile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean addStudent(Student student) {
        if (conn == null) {
            lastError = "Database connection is not established.";
            return false;
        }
        String query = "INSERT INTO students (full_name, student_id, degree, email, mobile) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, student.getFullName());
            ps.setString(2, student.getStudentId());
            ps.setString(3, student.getDegree());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getMobile());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET full_name=?, degree=?, email=?, mobile=? WHERE student_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, student.getFullName());
            ps.setString(2, student.getDegree());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getMobile());
            ps.setString(5, student.getStudentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean deleteStudent(String studentId) {
        String query = "DELETE FROM students WHERE student_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    // ================= LECTURER OPERATIONS =================

    public List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String query = "SELECT * FROM lecturers";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String coursesStr = rs.getString("courses_teaching");
                String[] courses = coursesStr != null ? coursesStr.split(",") : new String[0];
                lecturers.add(new Lecturer(
                        rs.getString("full_name"),
                        rs.getString("department"),
                        courses,
                        rs.getString("email"),
                        rs.getString("mobile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    public boolean addLecturer(Lecturer lecturer) {
        String query = "INSERT INTO lecturers (full_name, department, courses_teaching, email, mobile) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getDepartment());
            ps.setString(3, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(4, lecturer.getEmail());
            ps.setString(5, lecturer.getMobile());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean updateLecturer(Lecturer lecturer) {
        // Assuming email is the unique identifier or we need an ID.
        // Based on model, there is no explicit ID field like student_id.
        // I will use email as the identifier for update/delete as it's common.
        String query = "UPDATE lecturers SET full_name=?, department=?, courses_teaching=?, mobile=? WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getDepartment());
            ps.setString(3, String.join(",", lecturer.getCoursesTeaching()));
            ps.setString(4, lecturer.getMobile());
            ps.setString(5, lecturer.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean deleteLecturer(String email) {
        String query = "DELETE FROM lecturers WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    // ================= COURSE OPERATIONS =================

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("credits"),
                        rs.getString("lecturer")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean addCourse(Course course) {
        String query = "INSERT INTO courses (course_code, course_name, credits, lecturer) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, course.getCourseId());
            ps.setString(2, course.getCourseName());
            ps.setString(3, course.getCreditHours());
            ps.setString(4, course.getLecturerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean updateCourse(Course course) {
        String query = "UPDATE courses SET course_name=?, credits=?, lecturer=? WHERE course_code=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCreditHours());
            ps.setString(3, course.getLecturerId());
            ps.setString(4, course.getCourseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean deleteCourse(String courseCode) {
        String query = "DELETE FROM courses WHERE course_code=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, courseCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    // ================= DEPARTMENT OPERATIONS =================

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM departments";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String degreesStr = rs.getString("degrees");
                String[] degrees = degreesStr != null ? degreesStr.split(",") : new String[0];
                departments.add(new Department(
                        rs.getString("department_name"),
                        rs.getString("hod"),
                        degrees,
                        rs.getString("no_of_staff")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public boolean addDepartment(Department department) {
        String query = "INSERT INTO departments (department_name, hod, degrees, no_of_staff) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getHod());
            ps.setString(3, String.join(",", department.getDegrees()));
            ps.setString(4, department.getNoOfStaff());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean updateDepartment(Department department) {
        // Assuming department_name is the PK
        String query = "UPDATE departments SET hod=?, degrees=?, no_of_staff=? WHERE department_name=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, department.getHod());
            ps.setString(2, String.join(",", department.getDegrees()));
            ps.setString(3, department.getNoOfStaff());
            ps.setString(4, department.getDepartmentName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean deleteDepartment(String departmentName) {
        String query = "DELETE FROM departments WHERE department_name=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, departmentName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    // ================= DEGREE OPERATIONS =================

    public List<Degree> getAllDegrees() {
        List<Degree> degrees = new ArrayList<>();
        String query = "SELECT * FROM degrees";
        try (PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                degrees.add(new Degree(
                        rs.getString("degree_name"),
                        rs.getString("department"),
                        rs.getString("no_of_students")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return degrees;
    }

    public boolean addDegree(Degree degree) {
        String query = "INSERT INTO degrees (degree_name, department, no_of_students) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, degree.getDegreeName());
            ps.setString(2, degree.getDepartment());
            ps.setString(3, degree.getNoOfStudents());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean updateDegree(Degree degree) {
        // Assuming degree_name is PK
        String query = "UPDATE degrees SET department=?, no_of_students=? WHERE degree_name=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, degree.getDepartment());
            ps.setString(2, degree.getNoOfStudents());
            ps.setString(3, degree.getDegreeName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean deleteDegree(String degreeName) {
        String query = "DELETE FROM degrees WHERE degree_name=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, degreeName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            lastError = e.getMessage();
            return false;
        }
    }
}

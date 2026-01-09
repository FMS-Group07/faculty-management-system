package com.faculty.controller;

import com.faculty.dao.AdminDashboardView_DAO;
import com.faculty.model.*;
import com.faculty.view.*;

import javax.swing.*;

import java.util.List;

public class AdminDashboardView_Controller {

    private AdminDashboardView view;
    private AdminDashboardView_DAO dao;
    private String currentView = "Students"; // Default view

    public AdminDashboardView_Controller(AdminDashboardView view) {
        this.view = view;
        this.dao = new AdminDashboardView_DAO();
        initController();
    }

    private void initController() {
        // Sidebar Listeners
        view.getStudentsBtn().addActionListener(e -> switchView("Students"));
        view.getLecturersBtn().addActionListener(e -> switchView("Lecturers"));
        view.getCoursesBtn().addActionListener(e -> switchView("Courses"));
        view.getDepartmentsBtn().addActionListener(e -> switchView("Departments"));
        view.getDegreesBtn().addActionListener(e -> switchView("Degrees"));

        // Action Buttons
        view.getAddBtn().addActionListener(e -> handleAdd());
        view.getEditBtn().addActionListener(e -> handleEdit());
        view.getDeleteBtn().addActionListener(e -> handleDelete());
        view.getSaveBtn().addActionListener(e -> refreshTable()); // Using Save as Refresh for now or placeholder
        view.getLogoutBtn().addActionListener(e -> handleLogout());

        // Initial Load
        switchView("Students");
    }

    private void switchView(String viewName) {
        this.currentView = viewName;
        view.setActiveSidebarButton(viewName);
        view.setTitle(viewName);
        refreshTable();
    }

    private void refreshTable() {
        switch (currentView) {
            case "Students":
                loadStudents();
                break;
            case "Lecturers":
                loadLecturers();
                break;
            case "Courses":
                loadCourses();
                break;
            case "Departments":
                loadDepartments();
                break;
            case "Degrees":
                loadDegrees();
                break;
        }
    }

    // ================= LOAD DATA METHODS =================

    private void loadStudents() {
        List<Student> list = dao.getAllStudents();
        String[] columns = { "Full Name", "Student ID", "Degree", "Email", "Mobile" };
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            data[i] = new Object[] { s.getFullName(), s.getStudentId(), s.getDegree(), s.getEmail(), s.getMobile() };
        }
        view.updateTableData(data, columns);
    }

    private void loadLecturers() {
        List<Lecturer> list = dao.getAllLecturers();
        String[] columns = { "Full Name", "Department", "Courses", "Email", "Mobile" };
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Lecturer l = list.get(i);
            data[i] = new Object[] { l.getFullName(), l.getDepartment(), String.join(", ", l.getCoursesTeaching()),
                    l.getEmail(), l.getMobile() };
        }
        view.updateTableData(data, columns);
    }

    private void loadCourses() {
        List<Course> list = dao.getAllCourses();
        String[] columns = { "Course Code", "Course Name", "Credits", "Lecturer" };
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            data[i] = new Object[] { c.getCourseId(), c.getCourseName(), c.getCreditHours(), c.getLecturerId() };
        }
        view.updateTableData(data, columns);
    }

    private void loadDepartments() {
        List<Department> list = dao.getAllDepartments();
        String[] columns = { "Department Name", "HOD", "Degrees", "No of Staff" };
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Department d = list.get(i);
            data[i] = new Object[] { d.getDepartmentName(), d.getHod(), String.join(", ", d.getDegrees()),
                    d.getNoOfStaff() };
        }
        view.updateTableData(data, columns);
    }

    private void loadDegrees() {
        List<Degree> list = dao.getAllDegrees();
        String[] columns = { "Degree Name", "Department", "No of Students" };
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Degree d = list.get(i);
            data[i] = new Object[] { d.getDegreeName(), d.getDepartment(), d.getNoOfStudents() };
        }
        view.updateTableData(data, columns);
    }

    // ================= ACTION HANDLERS =================

    private void handleAdd() {
        switch (currentView) {
            case "Students":
                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Add Student", null);
                sDialog.setVisible(true);
                if (sDialog.isSaved()) {
                    Object[] data = sDialog.getData();
                    Student s = new Student((String) data[0], (String) data[1], (String) data[2], (String) data[3],
                            (String) data[4]);
                    if (dao.addStudent(s))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Student");
                }
                break;
            case "Lecturers":
                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Add Lecturer", null);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) {
                    Object[] data = lDialog.getData();
                    // Lecturer constructor: FullName, Dept, Courses[], Email, Mobile
                    // data from dialog probably strings. Courses might need splitting if it's a
                    // single string field
                    // Assuming dialog returns String for courses
                    String[] courses = ((String) data[2]).split(",");
                    Lecturer l = new Lecturer((String) data[0], (String) data[1], courses, (String) data[3],
                            (String) data[4]);
                    if (dao.addLecturer(l))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Lecturer");
                }
                break;
            case "Courses":
                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Add Course", null);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) {
                    Object[] data = cDialog.getData();
                    Course c = new Course((String) data[0], (String) data[1], (String) data[2], (String) data[3]);
                    if (dao.addCourse(c))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Course");
                }
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Add Department", null);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) {
                    Object[] data = dDialog.getData();
                    String[] degrees = ((String) data[2]).split(",");
                    Department d = new Department((String) data[0], (String) data[1], degrees, (String) data[3]);
                    if (dao.addDepartment(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Department");
                }
                break;
            case "Degrees":
                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Add Degree", null);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) {
                    Object[] data = deDialog.getData();
                    Degree d = new Degree((String) data[0], (String) data[1], (String) data[2]);
                    if (dao.addDegree(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Degree");
                }
                break;
        }
    }

    private void handleEdit() {
        int row = view.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a row to edit");
            return;
        }

        switch (currentView) {
            case "Students":
                Object[] sData = getRowData(row, 5);
                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Edit Student", sData);
                sDialog.setVisible(true);
                if (sDialog.isSaved()) {
                    Object[] data = sDialog.getData();
                    Student s = new Student((String) data[0], (String) data[1], (String) data[2], (String) data[3],
                            (String) data[4]);
                    if (dao.updateStudent(s))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Student");
                }
                break;
            case "Lecturers":
                Object[] lData = getRowData(row, 5);
                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Edit Lecturer", lData);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) {
                    Object[] data = lDialog.getData();
                    String[] courses = ((String) data[2]).split(",");
                    Lecturer l = new Lecturer((String) data[0], (String) data[1], courses, (String) data[3],
                            (String) data[4]);
                    if (dao.updateLecturer(l))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Lecturer");
                }
                break;
            case "Courses":
                // Column order in table: "Course Code", "Course Name", "Credits", "Lecturer"
                // Course Model: Code, Name, Credits, Lecturer
                // CourseFormDialog getData order? Typically matched.
                // Assuming CourseFormDialog.getData() returns [Code, Name, Credits, Lecturer]
                Object[] cData = getRowData(row, 4);
                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Edit Course", cData);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) {
                    Object[] data = cDialog.getData();
                    Course c = new Course((String) data[0], (String) data[1], (String) data[2], (String) data[3]);
                    if (dao.updateCourse(c))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Course");
                }
                break;
            case "Departments":
                Object[] depData = getRowData(row, 4);
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Edit Department", depData);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) {
                    Object[] data = dDialog.getData();
                    String[] degrees = ((String) data[2]).split(",");
                    Department d = new Department((String) data[0], (String) data[1], degrees, (String) data[3]);
                    if (dao.updateDepartment(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Department");
                }
                break;
            case "Degrees":
                Object[] degData = getRowData(row, 3);
                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Edit Degree", degData);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) {
                    Object[] data = deDialog.getData();
                    Degree d = new Degree((String) data[0], (String) data[1], (String) data[2]);
                    if (dao.updateDegree(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Degree");
                }
                break;
        }
    }

    private void handleDelete() {
        int row = view.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a row to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view.getFrame(), "Are you sure you want to delete this record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        boolean success = false;
        switch (currentView) {
            case "Students":
                // Student ID is at column 1
                String studentId = (String) view.getValueAt(row, 1);
                success = dao.deleteStudent(studentId);
                break;
            case "Lecturers":
                // Email is at column 3 (Model: FullName, Dept, Courses, Email, Mobile)
                String lEmail = (String) view.getValueAt(row, 3);
                success = dao.deleteLecturer(lEmail);
                break;
            case "Courses":
                // Course Code is at column 0
                String courseCode = (String) view.getValueAt(row, 0);
                success = dao.deleteCourse(courseCode);
                break;
            case "Departments":
                String deptName = (String) view.getValueAt(row, 0);
                success = dao.deleteDepartment(deptName);
                break;
            case "Degrees":
                String degreeName = (String) view.getValueAt(row, 0);
                success = dao.deleteDegree(degreeName);
                break;
        }

        if (success) {
            refreshTable();
            JOptionPane.showMessageDialog(view.getFrame(), "Deleted successfully");
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Failed to delete");
        }
    }

    private void handleLogout() {
        view.getFrame().dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
    }

    private Object[] getRowData(int row, int cols) {
        Object[] data = new Object[cols];
        for (int i = 0; i < cols; i++) {
            data[i] = view.getValueAt(row, i);
        }
        return data;
    }
}

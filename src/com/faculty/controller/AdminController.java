package com.faculty.controller;

import com.faculty.view.*;
import javax.swing.JOptionPane;

public class AdminController {

    private AdminDashboardView view;
    private String currentView = "Students";

    // ================= DATA ARRAYS (Simulated Database) =================
    private String[] studentColumns = { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
    private Object[][] studentData = {
            { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumar@kln.ac.lk", "0123456789" },
            { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk", "0123456788" },
            { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk", "0123456787" },
    };

    private String[] lecturerColumns = { "Full Name", "Department", "Courses Teaching", "Email", "Mobile Number" };
    private Object[][] lecturerData = {
            { "Dr. Nuwan Kodagoda", "Computing", "OOP, DSA", "nuwan@kln.ac.lk", "0712345678" },
            { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" },
            { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" }
    };

    private String[] courseColumns = { "Course Code", "Course Name", "Credits", "Lecturer" };
    private Object[][] courseData = {
            { "SENG 11223", "Object Oriented Programming", "3", "Dr. Nuwan Kodagoda" },
            { "SENG 11213", "Data Structures and Algorithms", "3", "Dr. Nuwan Kodagoda" },
            { "SENG 11233", "Database Management Systems", "3", "Dr. Pradeepa Samarasinghe" }
    };

    private String[] departmentColumns = { "Department Name", "HOD", "Degrees", "No of Staff" };
    private Object[][] departmentData = {
            { "Computing", "Dr. Nuwan Kodagoda", "SE, CS, IS", "15" },
            { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" },
            { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" }
    };

    private String[] degreeColumns = { "Degree Name", "Department", "No of Students" };
    private Object[][] degreeData = {
            { "Software Engineering", "Computing", "150" },
            { "Computer Science", "Computing", "120" },
            { "Information Systems", "Computing", "100" },
            { "Bet", "Engineering Tech", "200" }
    };

    public AdminController(AdminDashboardView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Initial View Load
        switchView("Students");

        // Sidebar Listeners
        view.getStudentsBtn().addActionListener(e -> switchView("Students"));
        view.getLecturersBtn().addActionListener(e -> switchView("Lecturers"));
        view.getCoursesBtn().addActionListener(e -> switchView("Courses"));
        view.getDepartmentsBtn().addActionListener(e -> switchView("Departments"));
        view.getDegreesBtn().addActionListener(e -> switchView("Degrees"));

        // Action Buttons Listeners
        view.getAddBtn().addActionListener(e -> handleAdd());
        view.getEditBtn().addActionListener(e -> handleEdit());
        view.getDeleteBtn().addActionListener(e -> handleDelete());

        // Logout
        view.getLogoutBtn().addActionListener(e -> handleLogout());
    }

    private void switchView(String viewName) {
        this.currentView = viewName;
        view.setTitle(viewName);
        view.setActiveSidebarButton(viewName);

        switch (viewName) {
            case "Students":
                view.updateTableData(studentData, studentColumns);
                break;
            case "Lecturers":
                view.updateTableData(lecturerData, lecturerColumns);
                break;
            case "Courses":
                view.updateTableData(courseData, courseColumns);
                break;
            case "Departments":
                view.updateTableData(departmentData, departmentColumns);
                break;
            case "Degrees":
                view.updateTableData(degreeData, degreeColumns);
                break;
        }
    }

    private void handleAdd() {
        switch (currentView) {
            case "Students":
                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Add Student", null);
                sDialog.setVisible(true);
                if (sDialog.isSaved())
                    view.addRow(sDialog.getData());
                break;
            case "Lecturers":
                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Add Lecturer", null);
                lDialog.setVisible(true);
                if (lDialog.isSaved())
                    view.addRow(lDialog.getData());
                break;
            case "Courses":
                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Add Course", null);
                cDialog.setVisible(true);
                if (cDialog.isSaved())
                    view.addRow(cDialog.getData());
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Add Department", null);
                dDialog.setVisible(true);
                if (dDialog.isSaved())
                    view.addRow(dDialog.getData());
                break;
            case "Degrees":
                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Add Degree", null);
                deDialog.setVisible(true);
                if (deDialog.isSaved())
                    view.addRow(deDialog.getData());
                break;
        }
    }

    private void handleEdit() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a record to edit.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int colCount = view.getColumnCount();
        Object[] currentData = new Object[colCount];
        for (int i = 0; i < colCount; i++) {
            currentData[i] = view.getValueAt(selectedRow, i);
        }

        switch (currentView) {
            case "Students":
                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Edit Student", currentData);
                sDialog.setVisible(true);
                if (sDialog.isSaved())
                    view.updateRow(selectedRow, sDialog.getData());
                break;
            case "Lecturers":
                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Edit Lecturer", currentData);
                lDialog.setVisible(true);
                if (lDialog.isSaved())
                    view.updateRow(selectedRow, lDialog.getData());
                break;
            case "Courses":
                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Edit Course", currentData);
                cDialog.setVisible(true);
                if (cDialog.isSaved())
                    view.updateRow(selectedRow, cDialog.getData());
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Edit Department",
                        currentData);
                dDialog.setVisible(true);
                if (dDialog.isSaved())
                    view.updateRow(selectedRow, dDialog.getData());
                break;
            case "Degrees":
                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Edit Degree", currentData);
                deDialog.setVisible(true);
                if (deDialog.isSaved())
                    view.updateRow(selectedRow, deDialog.getData());
                break;
        }
    }

    private void handleDelete() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a record to delete.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view.getFrame(),
                "Are you sure you want to delete this " + currentView.substring(0, currentView.length() - 1) + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            view.removeRow(selectedRow);
        }
    }

    private void handleLogout() {
        // For now, simpler logout - maybe close app or return to login if we had
        // reference
        // Since LoginController spawned us, we don't have direct back-link easily
        // unless we pass it.
        // Default behavior in button was just log out button existing.
        // Let's implement basic close or something.
        // Or ask user if they want to logout.
        int confirm = JOptionPane.showConfirmDialog(view.getFrame(),
                "Are you sure you want to Logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            view.getFrame().dispose();
            new LoginController(new LoginView()); // Return to Login
        }
    }
}

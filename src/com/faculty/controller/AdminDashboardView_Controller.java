package com.faculty.controller;

import com.faculty.view.*;
import com.faculty.dao.AdminDashboardView_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardView_Controller implements ActionListener {

    private AdminDashboardView view;
    private AdminDashboardView_DAO dao;
    private String currentViewMode = "Students"; // Tracks active tab

    public AdminDashboardView_Controller(AdminDashboardView view) {
        this.view = view;
        this.dao = new AdminDashboardView_DAO();

        initListeners();
        loadStudents(); // Load default view
    }

    private void initListeners() {
        // Navigation Buttons
        view.getBtnStudents().addActionListener(this);
        view.getBtnLecturers().addActionListener(this);
        view.getBtnCourses().addActionListener(this);
        view.getBtnDepartments().addActionListener(this);
        view.getBtnDegrees().addActionListener(this);
        view.getBtnLogout().addActionListener(this);

        // CRUD Buttons
        view.getBtnAdd().addActionListener(this);
        view.getBtnEdit().addActionListener(this);
        view.getBtnDelete().addActionListener(this);
    }

    private void loadStudents() {
        currentViewMode = "Students";
        view.setViewTitle("Students");
        view.updateTable(dao.getStudents(), dao.getStudentColumns());
        view.highlightSidebar(view.getBtnStudents());
    }

    private void loadLecturers() {
        currentViewMode = "Lecturers";
        view.setViewTitle("Lecturers");
        view.updateTable(dao.getLecturers(), dao.getLecturerColumns());
        view.highlightSidebar(view.getBtnLecturers());
    }

    private void loadCourses() {
        currentViewMode = "Courses";
        view.setViewTitle("Courses");
        view.updateTable(dao.getCourses(), dao.getCourseColumns());
        view.highlightSidebar(view.getBtnCourses());
    }

    private void loadDepartments() {
        currentViewMode = "Departments";
        view.setViewTitle("Departments");
        view.updateTable(dao.getDepartments(), dao.getDepartmentColumns());
        view.highlightSidebar(view.getBtnDepartments());
    }

    private void loadDegrees() {
        currentViewMode = "Degrees";
        view.setViewTitle("Degrees");
        view.updateTable(dao.getDegrees(), dao.getDegreeColumns());
        view.highlightSidebar(view.getBtnDegrees());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // --- Navigation ---
        if (src == view.getBtnStudents()) loadStudents();
        else if (src == view.getBtnLecturers()) loadLecturers();
        else if (src == view.getBtnCourses()) loadCourses();
        else if (src == view.getBtnDepartments()) loadDepartments();
        else if (src == view.getBtnDegrees()) loadDegrees();
        else if (src == view.getBtnLogout()) System.exit(0);

            // --- Actions ---
        else if (src == view.getBtnAdd()) handleAdd();
        else if (src == view.getBtnEdit()) handleEdit();
        else if (src == view.getBtnDelete()) handleDelete();
    }

    private void handleAdd() {
        switch (currentViewMode) {
            case "Students":
                StudentFormDialog sDialog = new StudentFormDialog(view, "Add Student", null);
                sDialog.setVisible(true);
                if (sDialog.isSaved()) view.getModel().addRow(sDialog.getData());
                break;
            case "Lecturers":
                LecturerFormDialog lDialog = new LecturerFormDialog(view, "Add Lecturer", null);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) view.getModel().addRow(lDialog.getData());
                break;
            case "Courses":
                CourseFormDialog cDialog = new CourseFormDialog(view, "Add Course", null);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) view.getModel().addRow(cDialog.getData());
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view, "Add Department", null);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) view.getModel().addRow(dDialog.getData());
                break;
            case "Degrees":
                DegreeFormDialog deDialog = new DegreeFormDialog(view, "Add Degree", null);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) view.getModel().addRow(deDialog.getData());
                break;
        }
    }

    private void handleEdit() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a record to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get current data
        int colCount = view.getTable().getColumnCount();
        Object[] currentData = new Object[colCount];
        for (int i = 0; i < colCount; i++) {
            currentData[i] = view.getModel().getValueAt(row, i);
        }

        switch (currentViewMode) {
            case "Students":
                StudentFormDialog sDialog = new StudentFormDialog(view, "Edit Student", currentData);
                sDialog.setVisible(true);
                if (sDialog.isSaved()) updateRow(row, sDialog.getData());
                break;
            case "Lecturers":
                LecturerFormDialog lDialog = new LecturerFormDialog(view, "Edit Lecturer", currentData);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) updateRow(row, lDialog.getData());
                break;
            case "Courses":
                CourseFormDialog cDialog = new CourseFormDialog(view, "Edit Course", currentData);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) updateRow(row, cDialog.getData());
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view, "Edit Department", currentData);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) updateRow(row, dDialog.getData());
                break;
            case "Degrees":
                DegreeFormDialog deDialog = new DegreeFormDialog(view, "Edit Degree", currentData);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) updateRow(row, deDialog.getData());
                break;
        }
    }

    private void handleDelete() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a record to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            view.getModel().removeRow(row);
        }
    }

    private void updateRow(int row, Object[] newData) {
        for (int i = 0; i < newData.length; i++) {
            view.getModel().setValueAt(newData[i], row, i);
        }
    }
}
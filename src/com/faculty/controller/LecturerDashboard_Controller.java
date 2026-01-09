package com.faculty.controller;

import com.faculty.dao.LecturerDashboard_DAO;
import com.faculty.model.Lecturer;
import com.faculty.view.LecturerDashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LecturerDashboard_Controller {

    private LecturerDashboard view;
    private LecturerDashboard_DAO dao;
    private String currentUserEmail;

    public LecturerDashboard_Controller(LecturerDashboard view, String username) {
        this.view = view;
        this.dao = new LecturerDashboard_DAO();
        this.currentUserEmail = username;

        initController();
    }

    private void initController() {
        loadDepartmentOptions();
        loadLecturerData();
        view.addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveProfile();
            }
        });
    }

    private void loadDepartmentOptions() {
        List<String> departments = dao.getAllDepartmentNames();
        view.setDepartmentOptions(departments);
    }

    private void loadLecturerData() {
        System.out.println("DEBUG: loadLecturerData called for email: [" + currentUserEmail + "]");
        Lecturer l = dao.getLecturerByEmail(currentUserEmail);

        if (l != null) {
            System.out.println("DEBUG: Lecturer found: " + l.getFullName());
            String degree = (l.getCoursesTeaching() != null && l.getCoursesTeaching().length > 0)
                    ? String.join(",", l.getCoursesTeaching())
                    : "";

            // Use empty strings instead of nulls to prevent setText crashes
            view.setLecturerData(
                    l.getFullName() != null ? l.getFullName() : "",
                    l.getLecturerId() != null ? l.getLecturerId() : "",
                    l.getDepartment() != null ? l.getDepartment() : "",
                    l.getEmail() != null ? l.getEmail() : "",
                    l.getMobile() != null ? l.getMobile() : "");
        } else {
            System.out.println("DEBUG: Lecturer NOT found. DAO Error: " + dao.getLastError());

            // Only show error, DO NOT clear fields (keeps user input visible for
            // debugging/retry)
            JOptionPane.showMessageDialog(view,
                    "Warning: Profile updated, but could not reload data for: " + currentUserEmail +
                            "\nDatabase Error: "
                            + (dao.getLastError().isEmpty() ? "Record not found" : dao.getLastError()));
        }
    }

    private void handleSaveProfile() {
        String[] data = view.getLecturerData();
        // [0]Name, [1]SalaryID, [2]Department, [3]Email, [4]Mobile

        // Construct Lecturer object
        // Note: Salary ID is not in Lecturer model constructor used here usually.
        // We'll ignore Salary ID for the Model if it's not supported by DB schema yet,
        // or add it later.

        String[] coursesPlaceholder = {}; // View doesn't edit courses yet?

        Lecturer l = new Lecturer(
                data[0], // Full Name
                data[1], // Lecturer ID (Salary ID)
                data[2], // Department
                coursesPlaceholder,
                data[3], // Email
                data[4] // Mobile
        );

        if (dao.getLecturerByEmail(currentUserEmail) == null) {
            // INSERT
            if (dao.addLecturer(l)) {
                JOptionPane.showMessageDialog(view, "Profile created successfully!");
                this.currentUserEmail = l.getEmail();
                loadLecturerData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to create profile: " + dao.getLastError());
            }
        } else {
            // UPDATE
            if (dao.updateLecturer(currentUserEmail, l)) {
                JOptionPane.showMessageDialog(view, "Profile updated successfully!");
                this.currentUserEmail = l.getEmail();
                loadLecturerData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update profile: " + dao.getLastError());
            }
        }
    }
}

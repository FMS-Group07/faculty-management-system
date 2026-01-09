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
        Lecturer l = dao.getLecturerByEmail(currentUserEmail);

        if (l != null) {
            view.setLecturerData(
                    l.getFullName() != null ? l.getFullName() : "",
                    l.getLecturerId() != null ? l.getLecturerId() : "",
                    l.getDepartment() != null ? l.getDepartment() : "",
                    l.getEmail() != null ? l.getEmail() : "",
                    l.getMobile() != null ? l.getMobile() : "");
        } else {
            JOptionPane.showMessageDialog(view,
                    "Warning: Profile updated, but could not reload data for: " + currentUserEmail +
                            "\nDatabase Error: "
                            + (dao.getLastError().isEmpty() ? "Record not found" : dao.getLastError()));
        }
    }

    private void handleSaveProfile() {
        String[] data = view.getLecturerData();
        String[] coursesPlaceholder = {};

        Lecturer l = new Lecturer(
                data[0],
                data[1],
                data[2],
                coursesPlaceholder,
                data[3],
                data[4]);

        if (dao.getLecturerByEmail(currentUserEmail) == null) {
            if (dao.addLecturer(l)) {
                JOptionPane.showMessageDialog(view, "Profile created successfully!");
                this.currentUserEmail = l.getEmail();
                loadLecturerData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to create profile: " + dao.getLastError());
            }
        } else {
            if (dao.updateLecturer(currentUserEmail, l)) {
                String warning = dao.getLastError();
                if (warning != null && !warning.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Profile updated successfully!\n" + warning);
                } else {
                    JOptionPane.showMessageDialog(view, "Profile updated successfully!");
                }
                this.currentUserEmail = l.getEmail();
                loadLecturerData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update profile: " + dao.getLastError());
            }
        }
    }
}
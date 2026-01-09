package com.faculty.controller;

import com.faculty.dao.StudentDashboard_DAO;
import com.faculty.model.Student;
import com.faculty.view.StudentDashBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard_Controller {

    private StudentDashBoard view;
    private StudentDashboard_DAO dao;
    private String currentUserEmail;

    public StudentDashboard_Controller(StudentDashBoard view, String username) {
        this.view = view;
        this.dao = new StudentDashboard_DAO();
        this.currentUserEmail = username; // Assuming username is email

        initController();
    }

    private void initController() {
        loadStudentData();
        view.addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveProfile();
            }
        });
    }

    private void loadStudentData() {
        Student s = dao.getStudentByEmail(currentUserEmail);
        if (s != null) {
            view.setStudentData(
                    s.getFullName(),
                    s.getStudentId(),
                    s.getDegree(),
                    s.getEmail(),
                    s.getMobile());
        } else {
            // New Profile Case
            view.setStudentData("", "", "", currentUserEmail, "");
            JOptionPane.showMessageDialog(view, "Welcome! Please complete your profile details.");
        }
    }

    private void handleSaveProfile() {
        String[] data = view.getStudentData();
        // data: [0]FullName, [1]StudentId, [2]Degree, [3]Email, [4]Mobile
        // Validations can go here

        Student s = new Student(
                data[0], // Name
                data[1], // ID (read-only ideally, but passed as reference)
                data[2], // Degree
                data[3], // Email
                data[4] // Mobile
        );

        if (dao.getStudentByEmail(currentUserEmail) == null) {
            // INSERT
            if (dao.addStudent(s)) {
                JOptionPane.showMessageDialog(view, "Profile created successfully!");
                this.currentUserEmail = s.getEmail();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to create profile: " + dao.getLastError());
            }
        } else {
            // UPDATE
            if (dao.updateStudent(currentUserEmail, s)) {
                JOptionPane.showMessageDialog(view, "Profile updated successfully!");
                // Update the current user email if it was changed, so next save works
                this.currentUserEmail = s.getEmail();
                loadStudentData(); // Refresh to be sure
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update profile: " + dao.getLastError());
            }
        }
    }
}

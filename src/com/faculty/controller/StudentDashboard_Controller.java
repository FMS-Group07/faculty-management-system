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
        this.currentUserEmail = username;

        initController();
    }

    private void initController() {
        loadDegreeOptions();
        loadStudentData();
        view.addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveProfile();
            }
        });
    }

    private void loadDegreeOptions() {
        java.util.List<String> degrees = dao.getAllDegreeNames();
        view.setDegreeOptions(degrees);
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
            view.setStudentData("", "", "", currentUserEmail, "");
            JOptionPane.showMessageDialog(view, "Welcome! Please complete your profile details.");
        }
    }

    private void handleSaveProfile() {
        String[] data = view.getStudentData();

        Student s = new Student(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4]);

        if (dao.getStudentByEmail(currentUserEmail) == null) {
            if (dao.addStudent(s)) {
                JOptionPane.showMessageDialog(view, "Profile created successfully!");
                this.currentUserEmail = s.getEmail();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to create profile: " + dao.getLastError());
            }
        } else {
            if (dao.updateStudent(currentUserEmail, s)) {
                JOptionPane.showMessageDialog(view, "Profile updated successfully!");
                this.currentUserEmail = s.getEmail();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update profile: " + dao.getLastError());
            }
        }
    }
}
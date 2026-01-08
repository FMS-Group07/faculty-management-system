package com.faculty.controller;

import com.faculty.view.StudentDashBoard;
import com.faculty.dao.StudentDashboard_DAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard_Controller implements ActionListener {

    private StudentDashBoard view;
    private StudentDashboard_DAO dao;

    public StudentDashboard_Controller(StudentDashBoard view) {
        this.view = view;
        this.dao = new StudentDashboard_DAO();

        initListeners();
        loadDashboardData();
    }

    private void initListeners() {
        view.getBtnProfile().addActionListener(this);
        view.getBtnTimeTable().addActionListener(this);
        view.getBtnCourses().addActionListener(this);
        view.getBtnExit().addActionListener(this);
    }

    private void loadDashboardData() {
        // 1. Profile
        String[] profile = dao.getProfileData();
        view.setProfileData(profile[0], profile[1], profile[2], profile[3], profile[4]);

        // 2. Courses
        view.updateCourseTable(dao.getEnrolledCourses());

        // 3. Time Table
        view.drawTimeTable(
                dao.getTimeTableColumns(),
                dao.getMorningData(),
                dao.getAfternoonData()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnProfile()) {
            view.switchPanel("Profile");
        }
        else if (e.getSource() == view.getBtnTimeTable()) {
            view.switchPanel("TimeTable");
        }
        else if (e.getSource() == view.getBtnCourses()) {
            view.switchPanel("Courses");
        }
        else if (e.getSource() == view.getBtnExit()) {
            System.exit(0);
        }
    }
}
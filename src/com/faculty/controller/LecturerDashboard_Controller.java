package com.faculty.controller;

import com.faculty.view.LecturerDashboard;
import com.faculty.dao.LecturerDashboard_DAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LecturerDashboard_Controller implements ActionListener {

    private LecturerDashboard view;
    private LecturerDashboard_DAO dao;

    public LecturerDashboard_Controller(LecturerDashboard view) {
        this.view = view;
        this.dao = new LecturerDashboard_DAO();

        initListeners();
        loadInitialData();
    }

    private void initListeners() {
        view.getBtnProfile().addActionListener(this);
        view.getBtnTimeTable().addActionListener(this);
        view.getBtnCourses().addActionListener(this);
        view.getBtnExit().addActionListener(this);
    }

    private void loadInitialData() {
        // Load Profile Data
        String[] profile = dao.getProfileData();
        view.setProfileData(profile[0], profile[1], profile[2], profile[3], profile[4]);

        // Load Course Data
        view.updateCourseTable(dao.getTeachingCourses(), dao.getCourseColumns());
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
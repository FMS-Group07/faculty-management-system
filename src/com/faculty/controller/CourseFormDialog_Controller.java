package com.faculty.controller;

import com.faculty.view.CourseFormDialog;
import com.faculty.dao.CourseFormDialog_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseFormDialog_Controller implements ActionListener {

    private CourseFormDialog view;
    private CourseFormDialog_DAO dao;

    public CourseFormDialog_Controller(CourseFormDialog view) {
        this.view = view;
        this.dao = new CourseFormDialog_DAO();

        initListeners();
    }

    private void initListeners() {
        view.getBtnSave().addActionListener(this);
        view.getBtnCancel().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnSave()) {
            handleSave();
        } else if (e.getSource() == view.getBtnCancel()) {
            handleCancel();
        }
    }

    private void handleSave() {
        // Extract data for validation
        String code = view.getCodeField().getText();
        String name = view.getNameField().getText();
        String credits = view.getCreditsField().getText();

        // Use DAO to validate
        if (dao.validateCourseData(code, name, credits)) {
            view.setSaved(true);
            view.dispose(); // Close dialog
        } else {
            JOptionPane.showMessageDialog(view,
                    "Please check your input.\nCredits must be numeric and fields cannot be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        view.setSaved(false);
        view.dispose(); // Close dialog
    }
}
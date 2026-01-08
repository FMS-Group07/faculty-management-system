package com.faculty.controller;

import com.faculty.view.LecturerFormDialog;
import com.faculty.dao.LecturerFormDialog_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LecturerFormDialog_Controller implements ActionListener {

    private LecturerFormDialog view;
    private LecturerFormDialog_DAO dao;

    public LecturerFormDialog_Controller(LecturerFormDialog view) {
        this.view = view;
        this.dao = new LecturerFormDialog_DAO();

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
        // Extract data from view
        String name = view.getNameField().getText();
        String dept = view.getDepartmentField().getText();
        String courses = view.getCoursesField().getText();
        String email = view.getEmailField().getText();
        String mobile = view.getMobileField().getText();

        // Validate via DAO
        if (dao.validateLecturerData(name, dept, courses, email, mobile)) {
            view.setSaved(true);
            view.dispose(); // Close dialog
        } else {
            JOptionPane.showMessageDialog(view,
                    "Invalid input.\nPlease fill all fields correctly.\nMobile number must contain digits only.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        view.setSaved(false);
        view.dispose(); // Close without saving
    }
}
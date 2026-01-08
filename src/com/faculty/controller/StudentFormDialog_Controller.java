package com.faculty.controller;

import com.faculty.view.StudentFormDialog;
import com.faculty.dao.StudentFormDialog_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentFormDialog_Controller implements ActionListener {

    private StudentFormDialog view;
    private StudentFormDialog_DAO dao;

    public StudentFormDialog_Controller(StudentFormDialog view) {
        this.view = view;
        this.dao = new StudentFormDialog_DAO();

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
        // Extract data
        String name = view.getNameField().getText();
        String id = view.getIdField().getText();
        String degree = view.getDegreeField().getText();
        String email = view.getEmailField().getText();
        String mobile = view.getMobileField().getText();

        // Validate via DAO
        if (dao.validateStudentData(name, id, degree, email, mobile)) {
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
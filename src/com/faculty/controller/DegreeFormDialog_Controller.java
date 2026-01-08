package com.faculty.controller;

import com.faculty.view.DegreeFormDialog;
import com.faculty.dao.DegreeFormDialog_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DegreeFormDialog_Controller implements ActionListener {

    private DegreeFormDialog view;
    private DegreeFormDialog_DAO dao;

    public DegreeFormDialog_Controller(DegreeFormDialog view) {
        this.view = view;
        this.dao = new DegreeFormDialog_DAO();

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
        String department = view.getDepartmentField().getText();
        String students = view.getStudentsField().getText();

        // Validate via DAO
        if (dao.validateDegreeData(name, department, students)) {
            view.setSaved(true);
            view.dispose(); // Close the dialog
        } else {
            JOptionPane.showMessageDialog(view,
                    "Invalid input.\nEnsure fields are not empty and 'No of Students' is a number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        view.setSaved(false);
        view.dispose(); // Close without saving
    }
}
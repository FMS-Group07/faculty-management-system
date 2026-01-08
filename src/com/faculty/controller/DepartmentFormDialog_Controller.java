package com.faculty.controller;

import com.faculty.view.DepartmentFormDialog;
import com.faculty.dao.DepartmentFormDialog_DAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepartmentFormDialog_Controller implements ActionListener {

    private DepartmentFormDialog view;
    private DepartmentFormDialog_DAO dao;

    public DepartmentFormDialog_Controller(DepartmentFormDialog view) {
        this.view = view;
        this.dao = new DepartmentFormDialog_DAO();

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
        String hod = view.getHodField().getText();
        String staff = view.getStaffField().getText();

        // Validate via DAO
        if (dao.validateDepartmentData(name, hod, staff)) {
            view.setSaved(true);
            view.dispose(); // Close dialog
        } else {
            JOptionPane.showMessageDialog(view,
                    "Invalid input.\nEnsure fields are not empty and 'No of Staff' is a number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        view.setSaved(false);
        view.dispose(); // Close without saving
    }
}
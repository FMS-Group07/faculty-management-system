package com.faculty.controller;

import com.faculty.view.LoginView;
import com.faculty.dao.UserDAO;
import com.faculty.model.User;

import javax.swing.*;

public class LoginController {

    private LoginView view;
    private UserDAO userDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.userDAO = new UserDAO();

        // Show the GUI
        this.view.show();

        // Attach listeners
        initController();
    }

    private void initController() {
        view.getSignInButton().addActionListener(e -> signIn());
        view.getSignUpButton().addActionListener(e -> signUp());
    }

    private void signIn() {
        String username = view.getSignInUsername();
        String password = view.getSignInPassword();
        String role = view.getSignInRole();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill all fields and select a role!");
            return;
        }

        boolean exists = userDAO.checkUserExists(username, password, role);
        if (exists) {
            openDashboard(role);
            // view.frame.dispose(); // Assuming we want to close login, but sticking to
            // logic fix for now
        } else {
            JOptionPane.showMessageDialog(null, "User not found! Please Sign Up.");
        }
    }

    private void signUp() {
        String username = view.getSignUpUsername();
        String password = view.getSignUpPassword();
        String confirm = view.getSignUpConfirm();
        String role = view.getSignUpRole();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fill all fields and select a role!");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!");
            return;
        }

        boolean saved = userDAO.register(new User(username, password, role));
        if (saved) {
            JOptionPane.showMessageDialog(null, "Sign Up successful! Please Sign In.");
            view.switchToSignInTab();
        } else {
            JOptionPane.showMessageDialog(null, "User already exists!");
        }
    }

    private void openDashboard(String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                JOptionPane.showMessageDialog(null, "Opening Admin Dashboard...");
                // new AdminDashboardView();
                break;
            case "STUDENT":
                JOptionPane.showMessageDialog(null, "Opening Student Dashboard...");
                // new StudentDashboardView();
                break;
            case "LECTURER":
                JOptionPane.showMessageDialog(null, "Opening Lecturer Dashboard...");
                // new LecturerDashboardView();
                break;
        }
    }
}

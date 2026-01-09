//Login Controller

package com.faculty.controller;

import com.faculty.view.AdminDashboardView;
import com.faculty.view.LecturerDashboard;
import com.faculty.view.LoginView;
import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import com.faculty.view.StudentDashBoard;

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
            JOptionPane.showMessageDialog(null, "Sign In successful!");
            openDashboard(role, username);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials or user does not exist!");
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

        int status = userDAO.register(new User(username, password, role));
        if (status == 0) {
            JOptionPane.showMessageDialog(null, "Sign Up successful! Please Sign In.");
            view.switchToSignInTab();
        } else if (status == 1) {
            JOptionPane.showMessageDialog(null, "User with this username already exists!");
        } else {
            JOptionPane.showMessageDialog(null, "Database Error! Check your connection.");
        }
    }

    private void openDashboard(String role, String username) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                JOptionPane.showMessageDialog(null, "Opening Admin Dashboard...");
                SwingUtilities.invokeLater(() -> {
                    AdminDashboardView adminView = new AdminDashboardView();
                    new AdminDashboardView_Controller(adminView);
                    adminView.setVisible(true);
                });
                break;
            case "STUDENT":
                JOptionPane.showMessageDialog(null, "Opening Student Dashboard...");
                SwingUtilities.invokeLater(() -> {
                    StudentDashBoard studentDash = new StudentDashBoard();
                    new StudentDashboard_Controller(studentDash, username);
                    studentDash.setVisible(true);
                });
                break;
            case "LECTURER":
                JOptionPane.showMessageDialog(null, "Opening Lecturer Dashboard...");
                SwingUtilities.invokeLater(() -> {
                    try {
                        LecturerDashboard lecturerView = new LecturerDashboard();
                        new LecturerDashboard_Controller(lecturerView, username);
                        lecturerView.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error opening dashboard: " + e.getMessage());
                    }
                });
                break;
        }
    }
}

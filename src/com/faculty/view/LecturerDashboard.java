package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LecturerDashboard extends JFrame {

    // UI Components accessible for Controller
    private JButton btnProfile, btnTimeTable, btnCourses, btnExit;
    private JPanel rightPanelContainer;
    private JPanel profileView, timeTableView, coursesView;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    // Form fields for Profile (Exposed for Controller if needed)
    private JTextField txtName, txtId, txtDept, txtEmail, txtMobile;

    public LecturerDashboard() {
        setTitle("Faculty Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setupSidebar();
        setupRightPanel();
    }

    private void setupSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(new Color(138, 43, 226)); // Purple
        sidebar.setPreferredSize(new Dimension(280, 600));

        JLabel iconLabel = new JLabel("👤", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setBounds(0, 35, 280, 105);
        sidebar.add(iconLabel);

        JLabel welcomeLabel = new JLabel("Welcome, Lucky", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(15, 125, 250, 30);
        sidebar.add(welcomeLabel);

        // Navigation Buttons
        btnProfile = createNavButton("👤    Profile Details", 180);
        btnTimeTable = createNavButton("\uD83D\uDCC5    Time Table", 230);
        btnCourses = createNavButton("\uD83D\uDCDA    Courses Teaching", 300);

        btnExit = new JButton("Exit");
        btnExit.setBounds(110, 440, 60, 60);

        try {
            ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.png"));
            Image scaledImage = exitIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            btnExit.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            btnExit.setText("Exit");
        }

        btnExit.setContentAreaFilled(false);
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setBackground(Color.WHITE);

        sidebar.add(btnProfile);
        sidebar.add(btnTimeTable);
        sidebar.add(btnCourses);
        sidebar.add(btnExit);

        add(sidebar, BorderLayout.WEST);
    }

    private void setupRightPanel() {
        rightPanelContainer = new JPanel(new CardLayout());
        rightPanelContainer.setBackground(Color.WHITE);

        initProfileView();
        initTimeTableView();
        initCoursesView();

        rightPanelContainer.add(profileView, "Profile");
        rightPanelContainer.add(timeTableView, "TimeTable");
        rightPanelContainer.add(coursesView, "Courses");

        add(rightPanelContainer, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setForeground(Color.GRAY);
        btn.setBackground(Color.WHITE);
        btn.setBounds(10, y, 260, 40);
        return btn;
    }

    private void initProfileView() {
        profileView = new JPanel(null);
        profileView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Profile Details", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 100, 300, 40);
        profileView.add(title);

        // Initialize fields (Controller will populate data, or keep defaults here)
        txtName = addFormRow(profileView, "Full Name", 180);
        txtId = addFormRow(profileView, "Salary ID", 230);
        txtDept = addFormRow(profileView, "Department", 280);
        txtEmail = addFormRow(profileView, "Email", 330);
        txtMobile = addFormRow(profileView, "Mobile Number", 380);

        JButton saveBtn = new JButton("Save changes");
        saveBtn.setBounds(160, 450, 400, 45);
        saveBtn.setBackground(new Color(138, 43, 226));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 20));
        profileView.add(saveBtn);
    }

    private JTextField addFormRow(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(80, y, 150, 30);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(138, 43, 226));
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(240, y, 350, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(138, 43, 226), 2, true));
        field.setForeground(new Color(138, 43, 226));
        panel.add(field);
        return field;
    }

    private void initTimeTableView() {
        Color purple = new Color(138, 43, 226);
        timeTableView = new JPanel(null);
        timeTableView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Time table", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(purple);
        title.setBounds(220, 100, 300, 40);
        timeTableView.add(title);

        // ... (Time Table drawing logic similar to original) ...
        // Simplified for brevity, you can paste the full loop logic back here
        // or let the View handle purely static drawing.
        createTimeTableGrid(timeTableView, purple);
    }

    private void createTimeTableGrid(JPanel panel, Color color) {
        // [Copy the loop logic from your original code here if needed]
        // For MVC, static drawing usually stays in View.
        int startX = 50, startY = 170, cellWidth = 100, cellHeight = 50;
        String[] cols = {"Time", "Mon", "Tue", "Wed", "Thu", "Fri"};
        for (int i = 0; i < cols.length; i++) {
            JLabel lbl = new JLabel(cols[i], SwingConstants.CENTER);
            lbl.setBounds(startX + (i * cellWidth), startY, cellWidth, cellHeight);
            lbl.setBorder(BorderFactory.createLineBorder(color));
            panel.add(lbl);
        }
    }

    private void initCoursesView() {
        coursesView = new JPanel(null);
        coursesView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Courses Teaching");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 100, 300, 40);
        coursesView.add(title);

        String[] columns = {"Course Code", "Course Name", "Credits", "Total Students"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(43);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(new Color(138, 43, 226));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(new Color(138, 43, 226));
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setGridColor(new Color(138, 43, 226));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 160, 580, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(138, 43, 226), 2));
        coursesView.add(scrollPane);
    }

    // --- HELPER METHODS FOR CONTROLLER ---

    public void switchPanel(String name) {
        CardLayout cl = (CardLayout) (rightPanelContainer.getLayout());
        cl.show(rightPanelContainer, name);

        // Update Button Colors
        Color purple = new Color(138, 43, 226);
        btnProfile.setForeground(name.equals("Profile") ? purple : Color.GRAY);
        btnTimeTable.setForeground(name.equals("TimeTable") ? purple : Color.GRAY);
        btnCourses.setForeground(name.equals("Courses") ? purple : Color.GRAY);
    }

    public void updateCourseTable(Object[][] data, String[] columns) {
        tableModel.setDataVector(data, columns);
    }

    public void setProfileData(String name, String id, String dept, String email, String mobile) {
        txtName.setText("  " + name);
        txtId.setText("  " + id);
        txtDept.setText("  " + dept);
        txtEmail.setText("  " + email);
        txtMobile.setText("  " + mobile);
    }

    // --- GETTERS ---
    public JButton getBtnProfile() { return btnProfile; }
    public JButton getBtnTimeTable() { return btnTimeTable; }
    public JButton getBtnCourses() { return btnCourses; }
    public JButton getBtnExit() { return btnExit; }
}
package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentDashBoard extends JFrame {

    // UI Components accessible for Controller
    private JButton btnProfile, btnTimeTable, btnCourses, btnExit;
    private JPanel rightPanelContainer;
    private JPanel profileView, timeTableView, coursesView;

    // Fields to update data
    private JTextField txtName, txtId, txtDegree, txtEmail, txtMobile;
    private DefaultTableModel courseTableModel;
    private JPanel timeTableGridPanel; // To add labels dynamically

    // Constants
    private final Color PURPLE_THEME = new Color(138, 43, 226);

    public StudentDashBoard() {
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
        sidebar.setBackground(PURPLE_THEME);
        sidebar.setPreferredSize(new Dimension(280, 600));

        JLabel iconLabel = new JLabel("👤", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setBounds(0, 35, 280, 105);
        sidebar.add(iconLabel);

        JLabel welcomeLabel = new JLabel("Welcome, Kumar", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(15, 125, 250, 30);
        sidebar.add(welcomeLabel);

        btnProfile = createNavButton("👤    Profile Details", 180);
        btnTimeTable = createNavButton("\uD83D\uDCC5    Time Table", 230);
        btnCourses = createNavButton("\uD83D\uDCDA    Course Enrolled", 300);

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
        title.setForeground(PURPLE_THEME);
        title.setBounds(220, 100, 300, 40);
        profileView.add(title);

        txtName = addFormRow(profileView, "Full Name", 180);
        txtId = addFormRow(profileView, "Student ID", 230);
        txtDegree = addFormRow(profileView, "Degree", 280);
        txtEmail = addFormRow(profileView, "Email", 330);
        txtMobile = addFormRow(profileView, "Mobile Number", 380);

        JButton saveBtn = new JButton("Save changes");
        saveBtn.setBounds(160, 450, 400, 45);
        saveBtn.setBackground(PURPLE_THEME);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 20));
        profileView.add(saveBtn);
    }

    private JTextField addFormRow(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(80, y, 150, 30);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(PURPLE_THEME);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(240, y, 350, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 2, true));
        field.setForeground(PURPLE_THEME);
        panel.add(field);
        return field;
    }

    private void initTimeTableView() {
        timeTableView = new JPanel(null);
        timeTableView.setBackground(Color.WHITE);

        // This panel will hold the grid labels
        timeTableGridPanel = new JPanel(null);
        timeTableGridPanel.setBackground(Color.WHITE);
        timeTableGridPanel.setBounds(0, 0, 1000, 650); // Cover parent
        timeTableView.add(timeTableGridPanel);

        JLabel title = new JLabel("Time table", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(PURPLE_THEME);
        title.setBounds(220, 100, 300, 40);
        timeTableGridPanel.add(title);
    }

    private void initCoursesView() {
        coursesView = new JPanel(null);
        coursesView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Enrolled Courses");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(PURPLE_THEME);
        title.setBounds(220, 100, 300, 40);
        coursesView.add(title);

        String[] columns = {"Course Code", "Course Name", "Credits", "Grade"};
        courseTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(courseTableModel);
        table.setRowHeight(43);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(PURPLE_THEME);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(PURPLE_THEME);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setGridColor(PURPLE_THEME);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 160, 580, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 2));
        coursesView.add(scrollPane);
    }

    // --- HELPER METHODS FOR CONTROLLER ---

    public void switchPanel(String name) {
        CardLayout cl = (CardLayout) (rightPanelContainer.getLayout());
        cl.show(rightPanelContainer, name);

        btnProfile.setForeground(name.equals("Profile") ? PURPLE_THEME : Color.GRAY);
        btnTimeTable.setForeground(name.equals("TimeTable") ? PURPLE_THEME : Color.GRAY);
        btnCourses.setForeground(name.equals("Courses") ? PURPLE_THEME : Color.GRAY);
    }

    public void setProfileData(String name, String id, String degree, String email, String mobile) {
        txtName.setText("  " + name);
        txtId.setText("  " + id);
        txtDegree.setText("  " + degree);
        txtEmail.setText("  " + email);
        txtMobile.setText("  " + mobile);
    }

    public void updateCourseTable(Object[][] data) {
        courseTableModel.setRowCount(0);
        for(Object[] row : data) {
            courseTableModel.addRow(row);
        }
    }

    // Draws the TimeTable grid based on data provided by Controller/DAO
    public void drawTimeTable(String[] cols, String[][] morningData, String[][] afternoonData) {
        // Clear previous grid items except title
        Component[] comps = timeTableGridPanel.getComponents();
        for(Component c : comps) {
            if(!(c instanceof JLabel && ((JLabel)c).getText().equals("Time table"))) {
                timeTableGridPanel.remove(c);
            }
        }

        int startX = 50;
        int startY = 170;
        int cellWidth = 100;
        int cellHeight = 50;

        // Draw Headers
        for (int i = 0; i < cols.length; i++) {
            JLabel lbl = new JLabel(cols[i], SwingConstants.CENTER);
            lbl.setBounds(startX + (i * cellWidth), startY, cellWidth, cellHeight);
            lbl.setBorder(BorderFactory.createLineBorder(PURPLE_THEME));
            lbl.setForeground(PURPLE_THEME);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            timeTableGridPanel.add(lbl);
        }

        int currentY = startY + cellHeight;

        // Draw Morning Data
        for (String[] row : morningData) {
            for (int i = 0; i < row.length; i++) {
                JLabel lbl = new JLabel(row[i], SwingConstants.CENTER);
                lbl.setBounds(startX + (i * cellWidth), currentY, cellWidth, cellHeight);
                lbl.setBorder(BorderFactory.createLineBorder(PURPLE_THEME));
                lbl.setForeground(PURPLE_THEME);
                timeTableGridPanel.add(lbl);
            }
            currentY += cellHeight;
        }

        // Draw Interval
        JLabel interval = new JLabel("Interval", SwingConstants.CENTER);
        interval.setBounds(startX, currentY, cellWidth * 6, cellHeight);
        interval.setOpaque(true);
        interval.setBackground(PURPLE_THEME);
        interval.setForeground(Color.WHITE);
        interval.setFont(new Font("Arial", Font.BOLD, 18));
        timeTableGridPanel.add(interval);

        currentY += cellHeight;

        // Draw Afternoon Data
        for (String[] row : afternoonData) {
            for (int i = 0; i < row.length; i++) {
                JLabel lbl = new JLabel(row[i], SwingConstants.CENTER);
                lbl.setBounds(startX + (i * cellWidth), currentY, cellWidth, cellHeight);
                lbl.setBorder(BorderFactory.createLineBorder(PURPLE_THEME));
                lbl.setForeground(PURPLE_THEME);
                timeTableGridPanel.add(lbl);
            }
            currentY += cellHeight;
        }

        timeTableGridPanel.repaint();
    }

    // --- GETTERS ---
    public JButton getBtnProfile() { return btnProfile; }
    public JButton getBtnTimeTable() { return btnTimeTable; }
    public JButton getBtnCourses() { return btnCourses; }
    public JButton getBtnExit() { return btnExit; }
}
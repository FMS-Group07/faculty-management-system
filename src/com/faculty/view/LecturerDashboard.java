package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
<<<<<<< HEAD:src/com/faculty/view/LecturerDashboardView.java
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

=======
>>>>>>> 68875536cc29aacafa76adc6cbdce1c3b1495074:src/com/faculty/view/LecturerDashboard.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

<<<<<<< HEAD:src/com/faculty/view/LecturerDashboardView.java
public class LecturerDashboardView extends JFrame implements ActionListener {
=======
public class LecturerDashboard extends JFrame implements ActionListener {
>>>>>>> 68875536cc29aacafa76adc6cbdce1c3b1495074:src/com/faculty/view/LecturerDashboard.java
    JButton btnProfile, btnTimeTable, btnCourses , btnExit ;
    JPanel rightPanelContainer;
    JPanel profileView;
    JPanel timeTableView;
    JPanel coursesView;

<<<<<<< HEAD:src/com/faculty/view/LecturerDashboardView.java
    public LecturerDashboardView() {
=======
    public LecturerDashboard() {
>>>>>>> 68875536cc29aacafa76adc6cbdce1c3b1495074:src/com/faculty/view/LecturerDashboard.java
        setTitle("Faculty Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//Frame make sure open in center of the display
        setLayout(new BorderLayout());

        //Left Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(new Color(138, 43, 226)); // Purple color
        sidebar.setPreferredSize(new Dimension(280, 600));

        // User Icon and Welcome Text
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

        // Navigation Buttons (Using helper method to style them)
        btnProfile = createNavButton("👤    Profile Details", 180);
        btnProfile.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnProfile.setForeground(new Color(138, 43, 226));
        btnProfile.setBounds(10, 200, 260, 40);

        btnTimeTable = createNavButton("\uD83D\uDCC5    Time Table", 230);
        btnTimeTable.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnTimeTable.setBounds(10, 260, 260, 40);

        btnCourses = createNavButton("\uD83D\uDCDA    Courses Teaching", 300);
        btnCourses.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnCourses.setBounds(10, 320, 260, 40);

        btnExit = new JButton("Exit");
        btnExit.setBounds(110, 440, 60, 60);


        ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.png"));
        Image scaledImage = exitIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        btnExit.setContentAreaFilled(false);
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setBackground(Color.WHITE);
        btnExit.setIcon(new ImageIcon(scaledImage));


        sidebar.add(btnProfile);
        sidebar.add(btnTimeTable);
        sidebar.add(btnCourses);
        sidebar.add(btnExit);

        // Add sidebar to the West (Left) of the frame
        add(sidebar, BorderLayout.WEST);

        //Right Content Panel
        rightPanelContainer = new JPanel();
        rightPanelContainer.setLayout(new CardLayout());
        rightPanelContainer.setBackground(Color.WHITE);

        // Initialize the three separate views
        initProfileView();
        initTimeTableView();
        initCoursesView();

        // Add views to the container
        rightPanelContainer.add(profileView, "Profile");
        rightPanelContainer.add(timeTableView, "TimeTable");
        rightPanelContainer.add(coursesView, "Courses");

        // Add the container to the Center of the frame
        add(rightPanelContainer, BorderLayout.CENTER);
    }


    private JButton createNavButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(this);
        btn.setForeground(Color.GRAY);
        btn.setBackground(Color.WHITE);
        return btn;
    }

    // --- View 1: Profile Details (Form) ---
    private void initProfileView() {
        profileView = new JPanel();
        profileView.setLayout(null);
        profileView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Profile Details",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 100, 300, 40);
        profileView.add(title);

        addFormRow(profileView, "Full Name", "Lucky Dias", 180);
        addFormRow(profileView, "Salary ID", "FCT_001", 230);
        addFormRow(profileView, "Department", "Computer Systems Engineering", 280);
        addFormRow(profileView, "Email", "luckydias@le.kln.ac.lk", 330);
        addFormRow(profileView, "Mobile Number", "0716656789", 380);


        JButton saveBtn = new JButton("Save changes");
        saveBtn.setBounds(160, 450, 400, 45);
        saveBtn.setBackground(new Color(138, 43, 226));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 20));
        profileView.add(saveBtn);
    }

    private void addFormRow(JPanel panel, String labelText, String value, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(80, y, 150, 30);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(138, 43, 226));
        panel.add(label);

        JTextField field = new JTextField("  "+value);
        field.setBounds(240, y, 350, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(138, 43, 226), 2,true));
        field.setForeground(new Color(138, 43, 226));
        panel.add(field);
    }

    //Time Table View
    private void initTimeTableView() {
        Color purple = new Color(138, 43, 226);

        timeTableView = new JPanel();
        timeTableView.setLayout(null);
        timeTableView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Time table",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 100, 300, 40);
        timeTableView.add(title);

        int startX = 50;
        int startY = 170;
        int cellWidth = 100;
        int cellHeight = 50;
        String[] cols = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (int i = 0; i < cols.length; i++) {
            JLabel lbl = new JLabel(cols[i], SwingConstants.CENTER);
            lbl.setBounds(startX + (i * cellWidth), startY, cellWidth, cellHeight);
            lbl.setBorder(BorderFactory.createLineBorder(purple));
            lbl.setForeground(purple);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            timeTableView.add(lbl);
        }

        String[][] Data = {
                {"08.00", "OOP", "OOP", "OOP", "OOP", "OOP"},
                {"10.00", "OOP", "OOP", "OOP", "OOP", "OOP"}
        };

        int currentY = startY + cellHeight;

        for (String[] row : Data) {
            for (int i = 0; i < row.length; i++) {
                JLabel lbl = new JLabel(row[i], SwingConstants.CENTER);
                lbl.setBounds(startX + (i * cellWidth), currentY, cellWidth, cellHeight);
                lbl.setBorder(BorderFactory.createLineBorder(purple));
                lbl.setForeground(purple);
                timeTableView.add(lbl);
            }
            currentY += cellHeight;
        }

        JLabel interval = new JLabel("Interval", SwingConstants.CENTER);
        interval.setBounds(startX, currentY, cellWidth * 6, cellHeight);
        interval.setOpaque(true);
        interval.setBackground(purple);
        interval.setForeground(Color.WHITE);
        interval.setFont(new Font("Arial", Font.BOLD, 18));
        timeTableView.add(interval);

        currentY += cellHeight;

        String[][] afternoonData = {
                {"01.00", "SE", "OOP", "SE", "SE", "SE"},
                {"03.00", "SE", "OOP", "SE", "SE", "SE"}
        };

        for (String[] row : afternoonData) {
            for (int i = 0; i < row.length; i++) {
                JLabel lbl = new JLabel(row[i], SwingConstants.CENTER);
                lbl.setBounds(startX + (i * cellWidth), currentY, cellWidth, cellHeight);
                lbl.setBorder(BorderFactory.createLineBorder(purple));
                lbl.setForeground(purple);
                timeTableView.add(lbl);
            }
            currentY += cellHeight;
        }
    }

    //Courses Enrolled
    private void initCoursesView() {
        coursesView = new JPanel();
        coursesView.setLayout(null); // keep your layout
        coursesView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Courses Teaching");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 100, 300, 40);
        coursesView.add(title);

        //TABLE PART
        String[] columns = {"Course Code", "Course Name", "Credits", "Total Students"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(43);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(new Color(138, 43, 226));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(new Color(138, 43, 226));
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setFont(table.getFont().deriveFont(Font.BOLD));
        table.setGridColor(new Color(138, 43, 226));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 160, 580, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(138, 43, 226), 2));

        coursesView.add(scrollPane);

        // TEMP DATA (remove later when DB is connected)
        loadMockCourses(model);
    }
    private void loadMockCourses(DefaultTableModel model) {
        model.addRow(new Object[]{"SE 21062", "OOP", 2, "60"});
        model.addRow(new Object[]{"OOP1  21052", "OOP", 2, "80"});
        model.addRow(new Object[]{"OOP2 21042", "OOP", 2, "85"});
        model.addRow(new Object[]{"SE 21032", "OOP", 2, "50"});
        model.addRow(new Object[]{"OOP 21022", "OOP", 2, "80"});
        model.addRow(new Object[]{"OOP 21012", "OOP", 2, "70"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout call = (CardLayout) (rightPanelContainer.getLayout());

        if (e.getSource() == btnProfile) {
            btnProfile.setForeground(new Color(138, 43, 226));
            btnTimeTable.setForeground(Color.GRAY);
            btnCourses.setForeground(Color.GRAY);
            call.show(rightPanelContainer, "Profile");
        }
        else if (e.getSource() == btnTimeTable) {
            btnTimeTable.setForeground(new Color(138, 43, 226));
            btnProfile.setForeground(Color.GRAY);
            btnCourses.setForeground(Color.GRAY);
            call.show(rightPanelContainer, "TimeTable");
        }
        else if (e.getSource() == btnCourses) {
            btnCourses.setForeground(new Color(138, 43, 226));
            btnProfile.setForeground(Color.GRAY);
            btnTimeTable.setForeground(Color.GRAY);
            call.show(rightPanelContainer, "Courses");
        }
    }
}
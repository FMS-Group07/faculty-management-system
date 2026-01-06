import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame implements ActionListener {
    JButton btnProfile, btnTimeTable, btnCourses , btnExit ;
    JPanel rightPanelContainer;
    JPanel profileView;
    JPanel timeTableView;
    JPanel coursesView;

    MainFrame() {
        setTitle("Faculty Management System");
        setSize(900, 600);
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
        iconLabel.setBounds(0, 20, 280, 105);
        sidebar.add(iconLabel);

        JLabel welcomeLabel = new JLabel("Welcome, Kumar", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(15, 110, 250, 30);
        sidebar.add(welcomeLabel);

        // Navigation Buttons (Using helper method to style them)
        btnProfile = createNavButton("👤    Profile Details", 180);
        btnProfile.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnProfile.setForeground(Color.GRAY);
        btnProfile.setBackground(Color.WHITE);
        btnProfile.setFocusPainted(false);
        btnProfile.setBorderPainted(false);
        btnProfile.setBounds(10, 180, 260, 40);

        btnTimeTable = createNavButton("\uD83D\uDCC5    Time Table", 230);
        btnTimeTable.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnTimeTable.setForeground(Color.GRAY);
        btnTimeTable.setBackground(Color.WHITE);
        btnTimeTable.setFocusPainted(false);
        btnTimeTable.setBorderPainted(false);
        btnTimeTable.setBounds(10, 240, 260, 40);

        btnCourses = createNavButton("\uD83D\uDCDA    Course Enrolled", 300);
        btnCourses.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnCourses.setForeground(Color.GRAY);
        btnCourses.setBackground(Color.WHITE);
        btnCourses.setFocusPainted(false);
        btnCourses.setBorderPainted(false);
        btnCourses.setBounds(10, 300, 260, 40);

        btnExit = new JButton("Exit");
        btnExit.setBounds(110, 400, 60, 60);


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
        btn.setBounds(30, y, 220, 45);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(138, 43, 226));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(this);
        return btn;
    }

    // --- View 1: Profile Details (Form) ---
    private void initProfileView() {
        profileView = new JPanel();
        profileView.setLayout(null);
        profileView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Profile Details");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 30, 200, 30);
        profileView.add(title);

        addFormRow(profileView, "Full Name", "Kumar Sangakkara", 100);
        addFormRow(profileView, "Student ID", "ET/2022/011", 150);
        addFormRow(profileView, "Degree", "Engineering Technology", 200);
        addFormRow(profileView, "Email", "kumars-et22011@stu.kln.ac.lk", 250);
        addFormRow(profileView, "Mobile Number", "0123456789", 300);


        JButton saveBtn = new JButton("Save changes");
        saveBtn.setBounds(150, 380, 350, 45);
        saveBtn.setBackground(new Color(138, 43, 226));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 16));
        profileView.add(saveBtn);
    }

    private void addFormRow(JPanel panel, String labelText, String value, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, y, 150, 30);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(138, 43, 226));
        panel.add(label);

        JTextField field = new JTextField("  "+value);
        field.setBounds(200, y, 350, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(138, 43, 226), 2,true));
        field.setForeground(new Color(138, 43, 226));
        panel.add(field);
    }

    // --- View 2: Time Table (JTable) ---
    private void initTimeTableView() {
        timeTableView = new JPanel();
        timeTableView.setLayout(new BorderLayout());
        timeTableView.setBackground(Color.WHITE);
        timeTableView.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Student Time Table", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(138, 43, 226));
        timeTableView.add(title, BorderLayout.NORTH);

        // Table Data (Slide 228 - JTable)
        String[] columns = {"Day", "Time", "Subject", "Hall"};
        String[][] data = {
                {"Monday", "08:00 - 10:00", "Object Oriented Programming", "Lab 01"},
                {"Monday", "10:30 - 12:30", "Database Systems", "Hall A"},
                {"Tuesday", "09:00 - 11:00", "Engineering Physics", "Lab 02"},
                {"Wednesday", "13:00 - 15:00", "Mathematics", "Hall B"},
                {"Friday", "08:00 - 10:00", "English", "Hall C"}
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setBackground(new Color(138, 43, 226));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        timeTableView.add(scrollPane, BorderLayout.CENTER);
    }

    //Courses Enrolled
    private void initCoursesView() {
        coursesView = new JPanel();
        coursesView.setLayout(null); // Absolute positioning for custom card look
        coursesView.setBackground(Color.WHITE);

        JLabel title = new JLabel("Enrolled Courses");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(138, 43, 226));
        title.setBounds(220, 30, 250, 30);
        coursesView.add(title);

        addCourseCard(coursesView, "CSCI 21052", "Object Oriented Programming I", "Status: Active", 100);
        addCourseCard(coursesView, "ENG 10222", "Engineering Physics", "Status: Active", 220);
        addCourseCard(coursesView, "MATH 20112", "Linear Algebra", "Status: Completed", 340);
    }

    private void addCourseCard(JPanel panel, String code, String name, String status, int y) {
        JTextArea card = new JTextArea();
        card.setText("\n  " + code + "\n  " + name + "\n\n  " + status);
        card.setBounds(100, y, 400, 100);
        card.setFont(new Font("Arial", Font.PLAIN, 15));
        card.setEditable(false);
        card.setBackground(new Color(245, 245, 255));
        card.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(138, 43, 226)));
        panel.add(card);
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

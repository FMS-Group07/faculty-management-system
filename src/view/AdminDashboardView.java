package view;

import utils.AppColors;
import utils.AppConfig;

import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminDashboardView {

    private String currentView = "Students";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboardView().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setUndecorated(true); // Remove default title bar
        frame.setSize(AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setShape(new RoundRectangle2D.Float(0, 0, AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT, 30, 30));

        // ================= CUSTOM TITLE BAR =================
        JPanel titleBar = createTitleBar(frame);
        frame.add(titleBar, BorderLayout.NORTH);

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(330, AppConfig.FRAME_HEIGHT - 30)); // Adjust for title bar height
        sidebar.setBackground(AppColors.PRIMARY_PURPLE);
        sidebar.setLayout(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        welcomeLabel.setBounds(20, 90, 290, 40);
        sidebar.add(welcomeLabel);

        // Sidebar Menu Items
        int yStart = 180;
        int gap = 60;

        JButton studentsBtn = addSidebarButton(sidebar, "Students", "👤", true, 65, yStart);
        JButton lecturersBtn = addSidebarButton(sidebar, "Lecturers", "👥", false, 65, yStart + gap);
        JButton coursesBtn = addSidebarButton(sidebar, "Courses", "📖", false, 65, yStart + gap * 2);
        JButton departmentsBtn = addSidebarButton(sidebar, "Departments", "🏢", false, 65, yStart + gap * 3);
        JButton degreesBtn = addSidebarButton(sidebar, "Degrees", "🎓", false, 65, yStart + gap * 4);

        // Logout Button (Bottom)
        LogoutButton logoutBtn = new LogoutButton();
        logoutBtn.setBounds(135, AppConfig.FRAME_HEIGHT - 100 - 30, 50, 50); // Adjust for title bar
        sidebar.add(logoutBtn);

        // ================= CENTER CONTENT =================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(AppColors.PRIMARY_PURPLE);
        titleLabel.setBounds(250, 30, 220, 50);
        centerPanel.add(titleLabel);

        // ================= TABLE =================
        String[] columns = { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
        Object[][] studentData = {
                { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumar@kln.ac.lk", "0123456789" },
                { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk", "0123456788" },
                { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk", "0123456787" },
        };

        String[] lecturerColumns = { "Full Name", "Department", "Courses Teaching", "Email", "Mobile Number" };
        Object[][] lecturerData = {
                { "Dr. Nuwan Kodagoda", "Computing", "OOP, DSA", "nuwan@kln.ac.lk", "0712345678" },
                { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" },
                { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" }
        };

        String[] courseColumns = { "Course Code", "Course Name", "Credits", "Lecturer" };
        Object[][] courseData = {
                { "SENG 11223", "Object Oriented Programming", "3", "Dr. Nuwan Kodagoda" },
                { "SENG 11213", "Data Structures and Algorithms", "3", "Dr. Nuwan Kodagoda" },
                { "SENG 11233", "Database Management Systems", "3", "Dr. Pradeepa Samarasinghe" }
        };

        String[] departmentColumns = { "Department Name", "HOD", "Degrees", "No of Staff" };
        Object[][] departmentData = {
                { "Computing", "Dr. Nuwan Kodagoda", "SE, CS, IS", "15" },
                { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" },
                { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" }
        };

        String[] degreeColumns = { "Degree Name", "Department", "No of Students" };
        Object[][] degreeData = {
                { "Software Engineering", "Computing", "150" },
                { "Computer Science", "Computing", "120" },
                { "Information Systems", "Computing", "100" },
                { "Bet", "Engineering Tech", "200" }
        };

        DefaultTableModel model = new DefaultTableModel(studentData, columns);
        JTable table = new JTable(model);

        // Sidebar Navigation Logic
        studentsBtn.addActionListener(e -> {
            currentView = "Students";
            titleLabel.setText("Students");
            model.setDataVector(studentData, columns);
            centerTableCells(table);

            // Update Styles
            studentsBtn.setBackground(AppColors.DARK_PURPLE);
            studentsBtn.setForeground(Color.WHITE);
            lecturersBtn.setBackground(Color.WHITE);
            lecturersBtn.setForeground(AppColors.PRIMARY_PURPLE);
            coursesBtn.setBackground(Color.WHITE);
            coursesBtn.setForeground(AppColors.PRIMARY_PURPLE);
            departmentsBtn.setBackground(Color.WHITE);
            departmentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            degreesBtn.setBackground(Color.WHITE);
            degreesBtn.setForeground(AppColors.PRIMARY_PURPLE);
        });

        lecturersBtn.addActionListener(e -> {
            currentView = "Lecturers";
            titleLabel.setText("Lecturers");
            model.setDataVector(lecturerData, lecturerColumns);
            centerTableCells(table);

            // Update Styles
            lecturersBtn.setBackground(AppColors.DARK_PURPLE);
            lecturersBtn.setForeground(Color.WHITE);
            studentsBtn.setBackground(Color.WHITE);
            studentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            coursesBtn.setBackground(Color.WHITE);
            coursesBtn.setForeground(AppColors.PRIMARY_PURPLE);
            departmentsBtn.setBackground(Color.WHITE);
            departmentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            degreesBtn.setBackground(Color.WHITE);
            degreesBtn.setForeground(AppColors.PRIMARY_PURPLE);
        });

        coursesBtn.addActionListener(e -> {
            currentView = "Courses";
            titleLabel.setText("Courses");
            model.setDataVector(courseData, courseColumns);
            centerTableCells(table);

            // Update Styles
            coursesBtn.setBackground(AppColors.DARK_PURPLE);
            coursesBtn.setForeground(Color.WHITE);
            studentsBtn.setBackground(Color.WHITE);
            studentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            lecturersBtn.setBackground(Color.WHITE);
            lecturersBtn.setForeground(AppColors.PRIMARY_PURPLE);
            departmentsBtn.setBackground(Color.WHITE);
            departmentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            degreesBtn.setBackground(Color.WHITE);
            degreesBtn.setForeground(AppColors.PRIMARY_PURPLE);
        });

        departmentsBtn.addActionListener(e -> {
            currentView = "Departments";
            titleLabel.setText("Departments");
            model.setDataVector(departmentData, departmentColumns);
            centerTableCells(table);

            // Update Styles
            departmentsBtn.setBackground(AppColors.DARK_PURPLE);
            departmentsBtn.setForeground(Color.WHITE);
            studentsBtn.setBackground(Color.WHITE);
            studentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            lecturersBtn.setBackground(Color.WHITE);
            lecturersBtn.setForeground(AppColors.PRIMARY_PURPLE);
            coursesBtn.setBackground(Color.WHITE);
            coursesBtn.setForeground(AppColors.PRIMARY_PURPLE);
            degreesBtn.setBackground(Color.WHITE);
            degreesBtn.setForeground(AppColors.PRIMARY_PURPLE);
        });

        degreesBtn.addActionListener(e -> {
            currentView = "Degrees";
            titleLabel.setText("Degrees");
            model.setDataVector(degreeData, degreeColumns);
            centerTableCells(table);

            // Update Styles
            degreesBtn.setBackground(AppColors.DARK_PURPLE);
            degreesBtn.setForeground(Color.WHITE);
            studentsBtn.setBackground(Color.WHITE);
            studentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
            lecturersBtn.setBackground(Color.WHITE);
            lecturersBtn.setForeground(AppColors.PRIMARY_PURPLE);
            coursesBtn.setBackground(Color.WHITE);
            coursesBtn.setForeground(AppColors.PRIMARY_PURPLE);
            departmentsBtn.setBackground(Color.WHITE);
            departmentsBtn.setForeground(AppColors.PRIMARY_PURPLE);
        });
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(AppColors.GRAY_BUTTON);
        table.setSelectionForeground(AppColors.DARK_PURPLE);
        table.setShowVerticalLines(true);
        table.setGridColor(AppColors.PRIMARY_PURPLE);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(AppColors.PRIMARY_PURPLE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(100, 50));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Center cell content
        centerTableCells(table);

        JScrollPane scrollPane = new JScrollPane(table);

        // Dynamic Table Height Calculation
        int rowHeight = 40;
        int headerHeight = 50;
        int maxVisibleRows = 7;
        int rows = model.getRowCount();

        int calculatedHeight = headerHeight + (rows * rowHeight);
        int maxHeight = headerHeight + (maxVisibleRows * rowHeight);

        scrollPane.setBounds(50, 210, 580, Math.min(calculatedHeight, maxHeight));
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        // Action Buttons
        int btnY = 120;
        int btnWidth = 150;
        int btnHeight = 45;
        int btnGap = 30;

        int panelWidth = AppConfig.FRAME_WIDTH - 330;
        int totalBtnWidth = (btnWidth * 3) + (btnGap * 2);
        int startX = (panelWidth - totalBtnWidth) / 2;

        JButton addBtn = createRoundedButton("Add new", AppColors.PRIMARY_PURPLE, Color.WHITE);
        addBtn.setBounds(startX, btnY, btnWidth, btnHeight);
        addBtn.addActionListener(e -> {
            switch (currentView) {
                case "Students":
                    StudentFormDialog sDialog = new StudentFormDialog(frame, "Add Student", null);
                    sDialog.setVisible(true);
                    if (sDialog.isSaved())
                        model.addRow(sDialog.getData());
                    break;
                case "Lecturers":
                    LecturerFormDialog lDialog = new LecturerFormDialog(frame, "Add Lecturer", null);
                    lDialog.setVisible(true);
                    if (lDialog.isSaved())
                        model.addRow(lDialog.getData());
                    break;
                case "Courses":
                    CourseFormDialog cDialog = new CourseFormDialog(frame, "Add Course", null);
                    cDialog.setVisible(true);
                    if (cDialog.isSaved())
                        model.addRow(cDialog.getData());
                    break;
                case "Departments":
                    DepartmentFormDialog dDialog = new DepartmentFormDialog(frame, "Add Department", null);
                    dDialog.setVisible(true);
                    if (dDialog.isSaved())
                        model.addRow(dDialog.getData());
                    break;
                case "Degrees":
                    DegreeFormDialog deDialog = new DegreeFormDialog(frame, "Add Degree", null);
                    deDialog.setVisible(true);
                    if (deDialog.isSaved())
                        model.addRow(deDialog.getData());
                    break;
            }
        });
        centerPanel.add(addBtn);

        JButton editBtn = createRoundedButton("Edit", AppColors.PRIMARY_PURPLE, Color.WHITE);
        editBtn.setBounds(startX + btnWidth + btnGap, btnY, btnWidth, btnHeight);
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a record to edit.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int colCount = table.getColumnCount();
            Object[] currentData = new Object[colCount];
            for (int i = 0; i < colCount; i++) {
                currentData[i] = model.getValueAt(selectedRow, i);
            }

            switch (currentView) {
                case "Students":
                    StudentFormDialog sDialog = new StudentFormDialog(frame, "Edit Student", currentData);
                    sDialog.setVisible(true);
                    if (sDialog.isSaved())
                        updateRow(model, selectedRow, sDialog.getData());
                    break;
                case "Lecturers":
                    LecturerFormDialog lDialog = new LecturerFormDialog(frame, "Edit Lecturer", currentData);
                    lDialog.setVisible(true);
                    if (lDialog.isSaved())
                        updateRow(model, selectedRow, lDialog.getData());
                    break;
                case "Courses":
                    CourseFormDialog cDialog = new CourseFormDialog(frame, "Edit Course", currentData);
                    cDialog.setVisible(true);
                    if (cDialog.isSaved())
                        updateRow(model, selectedRow, cDialog.getData());
                    break;
                case "Departments":
                    DepartmentFormDialog dDialog = new DepartmentFormDialog(frame, "Edit Department", currentData);
                    dDialog.setVisible(true);
                    if (dDialog.isSaved())
                        updateRow(model, selectedRow, dDialog.getData());
                    break;
                case "Degrees":
                    DegreeFormDialog deDialog = new DegreeFormDialog(frame, "Edit Degree", currentData);
                    deDialog.setVisible(true);
                    if (deDialog.isSaved())
                        updateRow(model, selectedRow, deDialog.getData());
                    break;
            }
        });
        centerPanel.add(editBtn);

        JButton deleteBtn = createRoundedButton("Delete", AppColors.PRIMARY_PURPLE, Color.WHITE);
        deleteBtn.setBounds(startX + (btnWidth + btnGap) * 2, btnY, btnWidth, btnHeight);
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a record to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this " + currentView.substring(0, currentView.length() - 1) + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
            }
        });
        centerPanel.add(deleteBtn);

        // ================= SAVE BUTTON =================
        JButton saveBtn = createRoundedButton("Save changes", AppColors.PRIMARY_PURPLE, Color.WHITE);
        saveBtn.setBounds(140, 420, 400, 50);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        centerPanel.add(saveBtn);

        // Add panels to frame
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private Point initialClick;

    private JPanel createTitleBar(JFrame frame) {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE); // Or AppColors.PRIMARY_PURPLE based on pref
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));

        // Window Title Centered
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Spacer - LEFT SIDE (to balance the controls and force title to absolute
        // center)
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(100, 30)); // Match controls width
        titleBar.add(spacer, BorderLayout.WEST);

        // Drag Functionality
        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                initialClick = e.getPoint();
                frame.getComponentAt(initialClick);
            }
        });

        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                // Get location of the window
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });

        return titleBar;
    }

    public class LogoutButton extends JButton {
        public LogoutButton() {
            super();
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100); // Fully rounded

            // Draw Icon
            g2.setColor(AppColors.PRIMARY_PURPLE);
            if (getModel().isPressed()) {
                g2.setColor(AppColors.DARK_PURPLE);
            }

            Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke);

            int size = 24;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            // Power Icon: Broken Circle
            // 90 is top. Start at 120, sweep 300. Gap at top.
            g2.drawArc(x, y, size, size, 120, 300);

            // Power Icon: Line
            // From top of bounding box down to center
            int cx = x + size / 2;
            int cy = y + size / 2;
            g2.drawLine(cx, y - 2, cx, cy);

            g2.dispose();
        }
    }

    public class RoundedButton extends JButton {

        private int radius = 20;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(new Color(25, 118, 210)); // Darker blue/pressed color
            } else if (getModel().isRollover()) {
                // If background is white (like inactive sidebar), maybe hover is light gray?
                // But user asked for specific action button colors.
                // Let's assume a generic hover behavior: slightly darker or complementary.
                // For Primary Purple buttons, let's use Dark Purple.
                if (getBackground().equals(AppColors.PRIMARY_PURPLE)) {
                    g2.setColor(AppColors.DARK_PURPLE);
                } else {
                    g2.setColor(getBackground().darker());
                }
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g);
            g2.dispose();
        }
    }

    private JButton addSidebarButton(JPanel panel, String text, String icon, boolean isActive, int x, int y) {
        JButton btn = new RoundedButton(text); // Icons typically require Font support or ImageIcon
        // For simplicity using text, but styling it like the image

        btn.setBounds(x, y, 200, 45);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 25));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);

        if (isActive) {
            btn.setBackground(AppColors.DARK_PURPLE);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(AppColors.WHITE); // Transparent-ish logic
            btn.setForeground(AppColors.PRIMARY_PURPLE);
        }

        panel.add(btn);
        return btn;
    }

    private void updateRow(DefaultTableModel model, int row, Object[] newData) {
        for (int i = 0; i < newData.length; i++) {
            model.setValueAt(newData[i], row, i);
        }
    }

    private void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton createRoundedButton(String text, Color bg, Color fg) {
        RoundedButton btn = new RoundedButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

}
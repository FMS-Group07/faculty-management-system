package com.faculty.view;

import utils.AppColors;
import utils.AppConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminDashboardView {

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
        sidebar.setPreferredSize(new Dimension(360, AppConfig.FRAME_HEIGHT - 30)); // Adjust for title bar height
        sidebar.setBackground(AppColors.PRIMARY_PURPLE);
        sidebar.setLayout(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        welcomeLabel.setBounds(35, 90, 290, 40);
        sidebar.add(welcomeLabel);

        // Sidebar Menu Items
        int yStart = 200;
        int gap = 60;

        addSidebarButton(sidebar, "Students", "👤", true, 80, yStart);
        addSidebarButton(sidebar, "Lecturers", "👥", false, 80, yStart + gap);
        addSidebarButton(sidebar, "Courses", "📖", false, 80, yStart + gap * 2);
        addSidebarButton(sidebar, "Departments", "🏢", false, 80, yStart + gap * 3);
        addSidebarButton(sidebar, "Degrees", "🎓", false, 80, yStart + gap * 4);

        // Logout Button (Bottom)
        RoundedButton logoutBtn = new RoundedButton("Logout");
        logoutBtn.setBounds(150, AppConfig.FRAME_HEIGHT - 100 - 30, 50, 50); // Adjust for title bar
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(AppColors.PRIMARY_PURPLE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        sidebar.add(logoutBtn);

        // ================= CENTER CONTENT =================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(AppColors.PRIMARY_PURPLE);
        titleLabel.setBounds(360, 30, 200, 50);
        centerPanel.add(titleLabel);

        // Action Buttons
        int btnY = 120;
        int btnWidth = 150;
        int btnHeight = 45;
        int btnGap = 30;

        // Center Panel Width approx calculation or fixed based on config
        // Frame: 1280, Sidebar: 360 => Center: 920
        int panelWidth = AppConfig.FRAME_WIDTH - 360;
        int totalBtnWidth = (btnWidth * 3) + (btnGap * 2);
        int startX = (panelWidth - totalBtnWidth) / 2;

        JButton addBtn = createRoundedButton("Add new", AppColors.PRIMARY_PURPLE, Color.WHITE);
        addBtn.setBounds(startX, btnY, btnWidth, btnHeight);
        centerPanel.add(addBtn);

        JButton editBtn = createRoundedButton("Edit", AppColors.PRIMARY_PURPLE, Color.WHITE);
        editBtn.setBounds(startX + btnWidth + btnGap, btnY, btnWidth, btnHeight);
        centerPanel.add(editBtn);

        JButton deleteBtn = createRoundedButton("Delete", AppColors.PRIMARY_PURPLE, Color.WHITE);
        deleteBtn.setBounds(startX + (btnWidth + btnGap) * 2, btnY, btnWidth, btnHeight);
        centerPanel.add(deleteBtn);

        // ================= TABLE =================
        String[] columns = { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
        Object[][] data = {
                { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumar@kln.ac.lk", "0123456789" },
                { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk", "0123456788" },
                { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk", "0123456787" },
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        // Dynamic Table Height Calculation
        int rowHeight = 40;
        int headerHeight = 50;
        int maxVisibleRows = 8;
        int rows = model.getRowCount();

        int calculatedHeight = headerHeight + (rows * rowHeight);
        int maxHeight = headerHeight + (maxVisibleRows * rowHeight);

        scrollPane.setBounds(50, 210, 800, Math.min(calculatedHeight, maxHeight));
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        // ================= SAVE BUTTON =================
        JButton saveBtn = createRoundedButton("Save changes", AppColors.PRIMARY_PURPLE, Color.WHITE);
        saveBtn.setBounds(250, 590, 400, 50);
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

        // Window Controls (Minimize, Close)
        // JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // controls.setOpaque(false);
        // controls.setPreferredSize(new Dimension(100, 30)); // Fixed width for
        // balancing

        // JButton minimizeBtn = new JButton("-");
        // minimizeBtn.setBorderPainted(false);
        // minimizeBtn.setContentAreaFilled(false);
        // minimizeBtn.setFocusPainted(false);
        // minimizeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // minimizeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // minimizeBtn.addActionListener(e -> frame.setState(Frame.ICONIFIED));

        // JButton closeBtn = new JButton("X");
        // closeBtn.setBorderPainted(false);
        // closeBtn.setContentAreaFilled(false);
        // closeBtn.setFocusPainted(false);
        // closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // closeBtn.setForeground(Color.RED);
        // closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // closeBtn.addActionListener(e -> System.exit(0));

        // controls.add(minimizeBtn);
        // controls.add(closeBtn);
        // titleBar.add(controls, BorderLayout.EAST);

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

    private void addSidebarButton(JPanel panel, String text, String icon, boolean isActive, int x, int y) {
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
    }

    private JButton createRoundedButton(String text, Color bg, Color fg) {
        RoundedButton btn = new RoundedButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

}
package view;

import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdminDashboardView {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboardView().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        final int FRAME_WIDTH = 1200;
        final int FRAME_HEIGHT = 750;

        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, FRAME_HEIGHT));
        sidebar.setBackground(AppColors.PRIMARY_PURPLE);
        sidebar.setLayout(null); // Using absolute layout for precise custom button positioning if needed, or Box

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setBounds(30, 40, 250, 30);
        sidebar.add(welcomeLabel);

        // Sidebar Menu Items
        int yStart = 100;
        int gap = 60; // larger gap for the big rounded buttons

        addSidebarButton(sidebar, "Students", "👤", true, 30, yStart);
        addSidebarButton(sidebar, "Lecturers", "👥", false, 30, yStart + gap);
        addSidebarButton(sidebar, "Courses", "📖", false, 30, yStart + gap * 2);
        addSidebarButton(sidebar, "Departments", "🏢", false, 30, yStart + gap * 3);
        addSidebarButton(sidebar, "Degrees", "🎓", false, 30, yStart + gap * 4);

        // Logout Button (Bottom)
        JButton logoutBtn = new JButton("Logout"); // or using icon
        logoutBtn.setBounds(30, FRAME_HEIGHT - 100, 50, 50);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(AppColors.DARK_PURPLE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        // Simple graphical tweak for round shape
        logoutBtn.setBorderPainted(false); // We'll make it specialized if needed
        sidebar.add(logoutBtn);

        // ================= CENTER CONTENT =================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null); // Using null layout for the specific positioning in the image

        // Title
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(AppColors.DARK_PURPLE);
        titleLabel.setBounds(400, 30, 200, 50); // Centered roughly
        centerPanel.add(titleLabel);

        // Action Buttons
        int btnY = 100;
        JButton addBtn = createRoundedButton("Add new", AppColors.PRIMARY_PURPLE, Color.WHITE);
        addBtn.setBounds(100, btnY, 150, 45);
        centerPanel.add(addBtn);

        JButton editBtn = createRoundedButton("Edit", AppColors.GRAY_BUTTON, Color.WHITE);
        editBtn.setBounds(270, btnY, 150, 45);
        centerPanel.add(editBtn);

        JButton deleteBtn = createRoundedButton("Delete", AppColors.GRAY_BUTTON, Color.WHITE);
        deleteBtn.setBounds(440, btnY, 150, 45);
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
        table.setSelectionBackground(AppColors.LIGHT_PURPLE);
        table.setSelectionForeground(AppColors.DARK_PURPLE);
        table.setShowVerticalLines(true);
        table.setGridColor(AppColors.PRIMARY_PURPLE);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(AppColors.DARK_PURPLE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(100, 50));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Center cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 180, 800, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        // ================= SAVE BUTTON =================
        JButton saveBtn = createRoundedButton("Save changes", AppColors.PRIMARY_PURPLE, Color.WHITE);
        saveBtn.setBounds(250, 520, 400, 50);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        centerPanel.add(saveBtn);

        // Add panels to frame
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addSidebarButton(JPanel panel, String text, String icon, boolean isActive, int x, int y) {
        JButton btn = new JButton(text); // Icons typically require Font support or ImageIcon
        // For simplicity using text, but styling it like the image

        btn.setBounds(x, y, 200, 45);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);

        if (isActive) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(AppColors.DARK_PURPLE);
        } else {
            btn.setBackground(AppColors.PRIMARY_PURPLE); // Transparent-ish logic
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true); // make sure it matches background
        }

        panel.add(btn);
    }

    private JButton createRoundedButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false); // Important for custom shapes
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
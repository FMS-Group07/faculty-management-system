package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.faculty.utils.AppColors;
import com.faculty.utils.AppConfig;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminDashboardView {

    private JFrame frame;
    private JLabel titleLabel;
    private JTable table;
    private DefaultTableModel model;
    private Point initialClick;

    // Sidebar Buttons
    private JButton studentsBtn;
    private JButton lecturersBtn;
    private JButton coursesBtn;
    private JButton departmentsBtn;
    private JButton degreesBtn;
    private LogoutButton logoutBtn;

    // Action Buttons
    private JButton addBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton saveBtn;

    public AdminDashboardView() {
        createGUI();
    }

    public void setVisible(boolean visible) {
        if (frame != null) {
            frame.setVisible(visible);
        }
    }

    private void createGUI() {
        frame = new JFrame("Admin Dashboard");
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

        studentsBtn = addSidebarButton(sidebar, "Students", "👤", true, 65, yStart);
        lecturersBtn = addSidebarButton(sidebar, "Lecturers", "👥", false, 65, yStart + gap);
        coursesBtn = addSidebarButton(sidebar, "Courses", "📖", false, 65, yStart + gap * 2);
        departmentsBtn = addSidebarButton(sidebar, "Departments", "🏢", false, 65, yStart + gap * 3);
        degreesBtn = addSidebarButton(sidebar, "Degrees", "🎓", false, 65, yStart + gap * 4);

        // Logout Button (Bottom)
        logoutBtn = new LogoutButton();
        logoutBtn.setBounds(135, AppConfig.FRAME_HEIGHT - 100 - 30, 50, 50); // Adjust for title bar
        sidebar.add(logoutBtn);

        // ================= CENTER CONTENT =================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null);

        // Title
        titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(AppColors.PRIMARY_PURPLE);
        titleLabel.setBounds(250, 30, 220, 50);
        centerPanel.add(titleLabel);

        // ================= TABLE =================
        model = new DefaultTableModel();
        table = new JTable(model);

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

        JScrollPane scrollPane = new JScrollPane(table);

        // Dynamic Table Height Calculation
        // Note: Initial size might be small if no data, but we set bounds explicitly
        scrollPane.setBounds(50, 210, 580, 400); // Set a fixed manageable size or dynamic based on logic
        // For now, let's keep it somewhat fixed or we can adjust dynamically if needed,
        // but dynamic height based on row count is tricky if rows change.
        // Let's use the bounds from before but we might need to update it when data
        // changes if we want that shrinking effect.
        // For simplicity layout, let's stick to a robust area.
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

        addBtn = createRoundedButton("Add new", AppColors.PRIMARY_PURPLE, Color.WHITE);
        addBtn.setBounds(startX, btnY, btnWidth, btnHeight);
        centerPanel.add(addBtn);

        editBtn = createRoundedButton("Edit", AppColors.PRIMARY_PURPLE, Color.WHITE);
        editBtn.setBounds(startX + btnWidth + btnGap, btnY, btnWidth, btnHeight);
        centerPanel.add(editBtn);

        deleteBtn = createRoundedButton("Delete", AppColors.PRIMARY_PURPLE, Color.WHITE);
        deleteBtn.setBounds(startX + (btnWidth + btnGap) * 2, btnY, btnWidth, btnHeight);
        centerPanel.add(deleteBtn);

        // ================= SAVE BUTTON =================
        saveBtn = createRoundedButton("Save changes", AppColors.PRIMARY_PURPLE, Color.WHITE);
        saveBtn.setBounds(140, 420, 400, 50);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        centerPanel.add(saveBtn);

        // Add panels to frame
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
    }

    // ================= PUBLIC METHODS FOR CONTROLLER =================

    public void updateTableData(Object[][] data, String[] columns) {
        model.setDataVector(data, columns);
        centerTableCells(table);
        // Recalculate scrollpane height if we want that dynamic effect?
        // The previous code did: scrollPane.setBounds(50, 210, 580,
        // Math.min(calculatedHeight, maxHeight));
        // But scrollPane is local in createGUI. We might need to promote it to field if
        // we want to resize it.
        // For now, let's just update data.
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setActiveSidebarButton(String viewName) {
        JButton[] encodedBtns = { studentsBtn, lecturersBtn, coursesBtn, departmentsBtn, degreesBtn };
        String[] names = { "Students", "Lecturers", "Courses", "Departments", "Degrees" };

        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(viewName)) {
                encodedBtns[i].setBackground(AppColors.DARK_PURPLE);
                encodedBtns[i].setForeground(Color.WHITE);
            } else {
                encodedBtns[i].setBackground(Color.WHITE);
                encodedBtns[i].setForeground(AppColors.PRIMARY_PURPLE);
            }
        }
    }

    public void updateRow(int row, Object[] newData) {
        for (int i = 0; i < newData.length; i++) {
            model.setValueAt(newData[i], row, i);
        }
    }

    public void addRow(Object[] rowData) {
        model.addRow(rowData);
    }

    public void removeRow(int row) {
        model.removeRow(row);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public int getColumnCount() {
        return table.getColumnCount();
    }

    public Object getValueAt(int row, int col) {
        return model.getValueAt(row, col);
    }

    // Getters for Listeners
    public JButton getStudentsBtn() {
        return studentsBtn;
    }

    public JButton getLecturersBtn() {
        return lecturersBtn;
    }

    public JButton getCoursesBtn() {
        return coursesBtn;
    }

    public JButton getDepartmentsBtn() {
        return departmentsBtn;
    }

    public JButton getDegreesBtn() {
        return degreesBtn;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JButton getEditBtn() {
        return editBtn;
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getLogoutBtn() {
        return logoutBtn;
    }

    public JFrame getFrame() {
        return frame;
    }

    // ================= PRIVATE HELPERS =================

    private JPanel createTitleBar(JFrame frame) {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE);
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(100, 30));
        titleBar.add(spacer, BorderLayout.WEST);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controlsPanel.setOpaque(false);
        controlsPanel.setPreferredSize(new Dimension(100, 30));

        JButton closeBtn = new JButton("X");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setOpaque(true);
        closeBtn.setBackground(Color.WHITE);
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setPreferredSize(new Dimension(45, 30));

        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                closeBtn.setBackground(new Color(232, 17, 35));
                closeBtn.setForeground(Color.WHITE);
                closeBtn.setContentAreaFilled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                closeBtn.setBackground(Color.WHITE);
                closeBtn.setForeground(Color.BLACK);
                closeBtn.setContentAreaFilled(false);
            }
        });

        closeBtn.addActionListener(e -> System.exit(0));

        controlsPanel.add(closeBtn);
        titleBar.add(controlsPanel, BorderLayout.EAST);

        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                initialClick = e.getPoint();
                frame.getComponentAt(initialClick);
            }
        });

        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
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

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);

            g2.setColor(AppColors.PRIMARY_PURPLE);
            if (getModel().isPressed()) {
                g2.setColor(AppColors.DARK_PURPLE);
            }

            Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke);

            int size = 24;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            g2.drawArc(x, y, size, size, 120, 300);

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
                g2.setColor(new Color(25, 118, 210));
            } else if (getModel().isRollover()) {
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
        JButton btn = new RoundedButton(text);
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
            btn.setBackground(AppColors.WHITE);
            btn.setForeground(AppColors.PRIMARY_PURPLE);
        }

        panel.add(btn);
        return btn;
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
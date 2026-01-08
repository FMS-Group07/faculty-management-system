package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.faculty.utils.AppColors;
import com.faculty.utils.AppConfig;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class AdminDashboardView extends JFrame {

    // UI Components accessible for Controller
    private JButton btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees;
    private JButton btnLogout;
    private JButton btnAdd, btnEdit, btnDelete;
    private JLabel titleLabel;
    private JTable table;
    private DefaultTableModel model;
    private Point initialClick;

    public AdminDashboardView() {
        // Frame Setup
        setTitle("Admin Dashboard");
        setUndecorated(true);
        setSize(AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setShape(new RoundRectangle2D.Float(0, 0, AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT, 30, 30));

        // Initialize UI
        add(createTitleBar(this), BorderLayout.NORTH);
        setupSidebar();
        setupCenterPanel();
    }

    // Used by Main/LoginController to show the window
    public void createAndShowGUI() {
        this.setVisible(true);
    }

    private void setupSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(330, AppConfig.FRAME_HEIGHT - 30));
        sidebar.setBackground(AppColors.PRIMARY_PURPLE);

        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        welcomeLabel.setBounds(20, 90, 290, 40);
        sidebar.add(welcomeLabel);

        int yStart = 180;
        int gap = 60;

        btnStudents = createSidebarButton("Students", "👤", true, 65, yStart);
        btnLecturers = createSidebarButton("Lecturers", "👥", false, 65, yStart + gap);
        btnCourses = createSidebarButton("Courses", "📖", false, 65, yStart + gap * 2);
        btnDepartments = createSidebarButton("Departments", "🏢", false, 65, yStart + gap * 3);
        btnDegrees = createSidebarButton("Degrees", "🎓", false, 65, yStart + gap * 4);

        sidebar.add(btnStudents);
        sidebar.add(btnLecturers);
        sidebar.add(btnCourses);
        sidebar.add(btnDepartments);
        sidebar.add(btnDegrees);

        btnLogout = new LogoutButton();
        btnLogout.setBounds(135, AppConfig.FRAME_HEIGHT - 130, 50, 50);
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);
    }

    private void setupCenterPanel() {
        JPanel centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setForeground(AppColors.PRIMARY_PURPLE);
        titleLabel.setBounds(250, 30, 220, 50);
        centerPanel.add(titleLabel);

        // Table Setup
        model = new DefaultTableModel();
        table = new JTable(model);
        styleTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 210, 580, 400); // Fixed height for simplicity
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane);

        // CRUD Buttons
        int btnY = 120;
        int btnWidth = 150;
        int btnHeight = 45;
        int btnGap = 30;
        int panelWidth = AppConfig.FRAME_WIDTH - 330;
        int totalBtnWidth = (btnWidth * 3) + (btnGap * 2);
        int startX = (panelWidth - totalBtnWidth) / 2;

        btnAdd = createRoundedButton("Add new", AppColors.PRIMARY_PURPLE, Color.WHITE);
        btnAdd.setBounds(startX, btnY, btnWidth, btnHeight);
        centerPanel.add(btnAdd);

        btnEdit = createRoundedButton("Edit", AppColors.PRIMARY_PURPLE, Color.WHITE);
        btnEdit.setBounds(startX + btnWidth + btnGap, btnY, btnWidth, btnHeight);
        centerPanel.add(btnEdit);

        btnDelete = createRoundedButton("Delete", AppColors.PRIMARY_PURPLE, Color.WHITE);
        btnDelete.setBounds(startX + (btnWidth + btnGap) * 2, btnY, btnWidth, btnHeight);
        centerPanel.add(btnDelete);

        add(centerPanel, BorderLayout.CENTER);
    }

    // --- HELPER METHODS FOR CONTROLLER ---

    public void updateTable(Object[][] data, String[] columns) {
        model.setDataVector(data, columns);
        centerTableCells();
    }

    public void setViewTitle(String title) {
        titleLabel.setText(title);
    }

    public void highlightSidebar(JButton activeBtn) {
        JButton[] allBtns = {btnStudents, btnLecturers, btnCourses, btnDepartments, btnDegrees};
        for (JButton btn : allBtns) {
            if (btn == activeBtn) {
                btn.setBackground(AppColors.DARK_PURPLE);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(Color.WHITE);
                btn.setForeground(AppColors.PRIMARY_PURPLE);
            }
        }
    }

    // --- GETTERS ---
    public JButton getBtnStudents() { return btnStudents; }
    public JButton getBtnLecturers() { return btnLecturers; }
    public JButton getBtnCourses() { return btnCourses; }
    public JButton getBtnDepartments() { return btnDepartments; }
    public JButton getBtnDegrees() { return btnDegrees; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

    // --- INTERNAL UI HELPERS ---

    private void styleTable() {
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(AppColors.GRAY_BUTTON);
        table.setSelectionForeground(AppColors.DARK_PURPLE);
        table.setShowVerticalLines(true);
        table.setGridColor(AppColors.PRIMARY_PURPLE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(AppColors.PRIMARY_PURPLE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(100, 50));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    private void centerTableCells() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton createSidebarButton(String text, String icon, boolean isActive, int x, int y) {
        RoundedButton btn = new RoundedButton(text);
        btn.setBounds(x, y, 200, 45);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 25));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        if (isActive) {
            btn.setBackground(AppColors.DARK_PURPLE);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(AppColors.PRIMARY_PURPLE);
        }
        return btn;
    }

    private JButton createRoundedButton(String text, Color bg, Color fg) {
        RoundedButton btn = new RoundedButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    private JPanel createTitleBar(JFrame frame) {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE);
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton closeBtn = new JButton("X");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(e -> System.exit(0));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controls.setOpaque(false);
        controls.add(closeBtn);
        titleBar.add(controls, BorderLayout.EAST);

        // Drag Logic
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { initialClick = e.getPoint(); }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                frame.setLocation(frame.getLocation().x + xMoved, frame.getLocation().y + yMoved);
            }
        });
        return titleBar;
    }

    // --- INNER CLASSES ---
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
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) g2.setColor(new Color(25, 118, 210));
            else if (getModel().isRollover()) {
                if (getBackground().equals(AppColors.PRIMARY_PURPLE)) g2.setColor(AppColors.DARK_PURPLE);
                else g2.setColor(getBackground().darker());
            } else g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public class LogoutButton extends JButton {
        public LogoutButton() {
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBackground(Color.WHITE);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);
            g2.setColor(AppColors.PRIMARY_PURPLE);
            if (getModel().isPressed()) g2.setColor(AppColors.DARK_PURPLE);
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int size = 24;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            g2.drawArc(x, y, size, size, 120, 300);
            g2.drawLine(x + size/2, y - 2, x + size/2, y + size/2);
            g2.dispose();
        }
    }
}
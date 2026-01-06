package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LecturerDashboardView extends JFrame {

    // --- CONSTANTS & COLORS ---
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 650;

    private final Color primaryPurple = new Color(124, 77, 255);
    private final Color darkPurple = new Color(45, 42, 78);
    private final Color lightPurple = new Color(235, 230, 255);
    private final Color grayButton = new Color(180, 180, 180);

    // --- LAYOUT COMPONENTS ---
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Map<String, JButton> menuButtons = new HashMap<>();

    public LecturerDashboardView() {
        setTitle("Admin Dashboard - Lecturers");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 1. Setup CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // 2. Add All Pages
        contentPanel.add(createPage("Students", getStudentColumns(), getStudentData()), "Students");
        contentPanel.add(createPage("Lecturers", getLecturerColumns(), getLecturerData()), "Lecturers");
        contentPanel.add(createPage("Courses", getCourseColumns(), getCourseData()), "Courses");
        contentPanel.add(createPage("Departments", getDeptColumns(), getDeptData()), "Departments");
        contentPanel.add(createPage("Degrees", getDegreeColumns(), getDegreeData()), "Degrees");

        // 3. Create Sidebar
        add(createSidebar(), BorderLayout.WEST);

        // 4. Add Main Content
        add(contentPanel, BorderLayout.CENTER);

        // 5. 🔥 SET DEFAULT VIEW TO "LECTURERS"
        cardLayout.show(contentPanel, "Lecturers");

        setLocationRelativeTo(null);
    }

    // ---------------- SIDEBAR ----------------
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(primaryPurple);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        // Menu Items
        String[] items = { "Students", "Lecturers", "Courses", "Departments", "Degrees" };
        String[] icons = { "👤", "👤", "📖", "🏛", "🎓" };

        for (int i = 0; i < items.length; i++) {
            String name = items[i];
            String icon = icons[i];

            // 🔥 CHECK: Set "Lecturers" as selected by default
            boolean isDefault = name.equals("Lecturers");

            JButton btn = createMenuButton(icon + "   " + name, name, isDefault);
            menuButtons.put(name, btn);

            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        sidebar.add(Box.createVerticalGlue());

        // --- MANUALLY DRAWN LOGOUT BUTTON ---
        JButton logoutBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.setColor(primaryPurple);
                g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                int size = 8;

                g2.drawLine(cx - size, cy, cx + size, cy);
                g2.drawLine(cx + size, cy, cx + 2, cy - 6);
                g2.drawLine(cx + size, cy, cx + 2, cy + 6);

                g2.dispose();
            }
        };

        logoutBtn.setPreferredSize(new Dimension(50, 50));
        logoutBtn.setMaximumSize(new Dimension(50, 50));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> System.exit(0));

        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton createMenuButton(String text, String cardName, boolean isSelected) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Dialog", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setMaximumSize(new Dimension(260, 50));
        btn.setPreferredSize(new Dimension(260, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 15, 0, 0));

        styleMenuButton(btn, isSelected);

        btn.addActionListener(e -> {
            cardLayout.show(contentPanel, cardName);
            updateSidebarStyles(cardName);
        });

        return btn;
    }

    private void styleMenuButton(JButton btn, boolean isSelected) {
        if (isSelected) {
            btn.setBackground(Color.WHITE); // Active: White BG
            btn.setForeground(primaryPurple); // Active: Purple Text
        } else {
            btn.setBackground(primaryPurple); // Inactive: Purple BG
            btn.setForeground(Color.WHITE); // Inactive: White Text
        }
    }

    private void updateSidebarStyles(String activeCard) {
        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            boolean isActive = entry.getKey().equals(activeCard);
            styleMenuButton(entry.getValue(), isActive);
        }
    }

    // ---------------- GENERIC PAGE CREATOR ----------------
    private JPanel createPage(String title, String[] columns, Object[][] data) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(primaryPurple);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Center Content
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(Color.WHITE);
        centerContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Action Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JButton btnAdd = createActionButton("Add new", primaryPurple);
        JButton btnEdit = createActionButton("Edit", grayButton);
        JButton btnDelete = createActionButton("Delete", grayButton);

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        centerContainer.add(btnPanel, BorderLayout.NORTH);

        // Table
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(primaryPurple, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        centerContainer.add(scrollPane, BorderLayout.CENTER);
        panel.add(centerContainer, BorderLayout.CENTER);

        // Save Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton saveBtn = createActionButton("Save changes", primaryPurple);
        saveBtn.setPreferredSize(new Dimension(250, 50));
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Changes saved successfully!"));

        bottomPanel.add(saveBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        return btn;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(45);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setGridColor(primaryPurple);
        table.setSelectionBackground(primaryPurple);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(primaryPurple);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    // --- DATA METHODS (Pre-filling the tables) ---

    private String[] getStudentColumns() {
        return new String[] { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
    }

    private Object[][] getStudentData() {
        return new Object[][] {
                { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumars@kln.ac.lk", "0123456789" },
                { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk", "0123456788" },
                { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk", "0123456787" }
        };
    }

    private String[] getLecturerColumns() {
        return new String[] { "Full Name", "Department", "Courses teaching", "Email", "Mobile Number" };
    }

    private Object[][] getLecturerData() {
        return new Object[][] {
                { "Kumar Sangakkara", "Software Engineering", "ETEC 21062", "kumars@kln.ac.lk", "0123456789" },
                { "Kumar Sangakkara", "Software Engineering", "CSCI 21052", "kumars@kln.ac.lk", "0123456789" },
                { "Kumar Sangakkara", "Software Engineering", "CSCI 21042", "kumars@kln.ac.lk", "0123456789" },
                { "Mithali Raj", "Applied Computing", "ETEC 21022", "mithalir@kln.ac.lk", "9876543210" }
        };
    }

    private String[] getCourseColumns() {
        return new String[] { "Course Code", "Course Name", "Credits", "Lecturer" };
    }

    private Object[][] getCourseData() {
        return new Object[][] {
                { "ETEC 21062", "OOP", "2", "Kumar Sanga" },
                { "ETEC 21052", "DSA", "2", "Kumar Sanga" },
                { "ETEC 21042", "Database", "2", "Kumar Sanga" }
        };
    }

    private String[] getDeptColumns() {
        return new String[] { "Name", "HOD", "Degree", "No of Staff" };
    }

    private Object[][] getDeptData() {
        return new Object[][] {
                { "Applied Computing", "Kumar Sanga", "Engineering Technology", "15" },
                { "Software Engineering", "Kumar Sanga", "Information Technology", "17" },
                { "Computer Systems Engineering", "Kumar Sanga", "Computer Science", "12" }
        };
    }

    private String[] getDegreeColumns() {
        return new String[] { "Degree", "Department", "No of Students" };
    }

    private Object[][] getDegreeData() {
        return new Object[][] {
                { "Engineering Technology", "Applied Computing", "375" },
                { "Information Technology", "Software Engineering", "375" },
                { "Computer Science", "Computer Systems Engineering", "325" },
                { "Bio Systems Technology", "Applied Computing", "75" }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LectureDashboardView().setVisible(true);
        });
    }
}
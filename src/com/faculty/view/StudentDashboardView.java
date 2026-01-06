package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StudentDashboardView extends JFrame {

    // --- 1. NEW CONSTANTS FOR FRAME SIZE ---
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 650;

    private DefaultTableModel tableModel;
    private JTable studentTable;
    private Color primaryPurple = new Color(124, 77, 255);
    private Color darkPurple = new Color(45, 42, 78);

    public StudentDashboardView() {
        setTitle("Admin Dashboard");

        // --- 2. APPLIED HERE ---
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create main content
        JPanel mainContent = createMainContent();
        add(mainContent, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(primaryPurple);
        // Adjusted width slightly since the frame is smaller now (optional but looks
        // better)
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Slightly smaller font
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        // Menu buttons
        String[] menuItems = { "👤 Students", "👤 Lecturers", "📖 Courses", "🏫 Departments", "🎓 Degrees" };
        for (int i = 0; i < menuItems.length; i++) {
            JButton btn = createMenuButton(menuItems[i], i == 0);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        sidebar.add(Box.createVerticalGlue());

        // ---------------------------------------------------------
        // LOGOUT BUTTON
        // ---------------------------------------------------------
        JButton logoutBtn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.setColor(primaryPurple);
                g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                int size = 10;

                g2.drawLine(cx - size, cy, cx + size, cy);
                g2.drawLine(cx + size, cy, cx + 2, cy - 8);
                g2.drawLine(cx + size, cy, cx + 2, cy + 8);

                g2.dispose();
            }
        };

        logoutBtn.setPreferredSize(new Dimension(60, 60));
        logoutBtn.setMaximumSize(new Dimension(60, 60));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn.addActionListener(e -> System.exit(0));

        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton createMenuButton(String text, boolean selected) {

        int radius = 35;

        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

                super.paintComponent(g2);
                g2.dispose();
            }
        };

        btn.setMaximumSize(new Dimension(290, 50)); // Made slightly shorter for smaller screen
        btn.setPreferredSize(new Dimension(290, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFont(new Font("Dialog", Font.BOLD, 18)); // Slightly smaller font
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);

        if (selected) {
            btn.setBackground(darkPurple);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(primaryPurple);
        }

        return btn;
    }

    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Reduced padding

        // -- 1. Header --
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Slightly smaller font
        titleLabel.setForeground(primaryPurple);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // -- 2. Center Panel --
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Top Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0)); // Reduced bottom space

        JButton addBtn = createActionButton("Add new");
        JButton editBtn = createActionButton("Edit");
        JButton deleteBtn = createActionButton("Delete");

        addBtn.addActionListener(e -> addNewStudent());
        editBtn.addActionListener(e -> editStudent());
        deleteBtn.addActionListener(e -> deleteStudent());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
        tableModel = new DefaultTableModel(columnNames, 0);
        addSampleData();

        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(45); // Adjusted row height
        studentTable.setGridColor(primaryPurple);
        studentTable.setForeground(primaryPurple);
        studentTable.setSelectionBackground(primaryPurple);
        studentTable.setSelectionForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = studentTable.getTableHeader();
        header.setBackground(primaryPurple);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(primaryPurple));
        scrollPane.getViewport().setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // -- 3. Bottom Panel (Save Button) --
        JButton saveBtn = new JButton("Save changes") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        saveBtn.setPreferredSize(new Dimension(200, 45)); // Smaller save button
        saveBtn.setBackground(primaryPurple);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 18));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setOpaque(false);

        saveBtn.addActionListener(e -> saveChanges());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        bottomPanel.add(saveBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(140, 40)); // Smaller action buttons
        btn.setBackground(primaryPurple);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        return btn;
    }

    private void addSampleData() {
        tableModel.addRow(new Object[] { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumar@kln.ac.lk",
                "0123456789" });
        tableModel.addRow(new Object[] { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk",
                "0123456788" });
        tableModel.addRow(new Object[] { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk",
                "0123456787" });
    }

    private void addNewStudent() {
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField degreeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField mobileField = new JTextField();

        Object[] message = {
                "Full Name:", nameField,
                "Student ID:", idField,
                "Degree:", degreeField,
                "Email:", emailField,
                "Mobile Number:", mobileField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[] {
                    nameField.getText(), idField.getText(), degreeField.getText(), emailField.getText(),
                    mobileField.getText()
            });
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JTextField idField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField degreeField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField emailField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        JTextField mobileField = new JTextField((String) tableModel.getValueAt(selectedRow, 4));

        Object[] message = {
                "Full Name:", nameField, "Student ID:", idField, "Degree:", degreeField, "Email:", emailField,
                "Mobile Number:", mobileField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(nameField.getText(), selectedRow, 0);
            tableModel.setValueAt(idField.getText(), selectedRow, 1);
            tableModel.setValueAt(degreeField.getText(), selectedRow, 2);
            tableModel.setValueAt(emailField.getText(), selectedRow, 3);
            tableModel.setValueAt(mobileField.getText(), selectedRow, 4);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void saveChanges() {
        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentDashboardView dashboard = new StudentDashboardView();
            dashboard.setVisible(true);
        });
    }
}
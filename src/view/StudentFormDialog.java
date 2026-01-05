package view;

import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentFormDialog extends JDialog {

    private boolean isSaved = false;
    private JTextField nameField;
    private JTextField idField;
    private JTextField degreeField;
    private JTextField emailField;
    private JTextField mobileField;

    public StudentFormDialog(Frame parent, String title, Object[] initialData) {
        super(parent, title, true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setUndecorated(true); // Custom look
        setLayout(new BorderLayout());

        // Rounded shape
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 400, 500, 20, 20));

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 5 fields + buttons

        nameField = createStyledTextField("Full Name");
        idField = createStyledTextField("Student ID");
        degreeField = createStyledTextField("Degree");
        emailField = createStyledTextField("Email");
        mobileField = createStyledTextField("Mobile Number");

        // Pre-fill data if editing
        if (initialData != null) {
            nameField.setText(initialData[0].toString());
            idField.setText(initialData[1].toString());
            degreeField.setText(initialData[2].toString());
            emailField.setText(initialData[3].toString());
            mobileField.setText(initialData[4].toString());
        }

        mainPanel.add(createFieldPanel("Full Name", nameField));
        mainPanel.add(createFieldPanel("Student ID", idField));
        mainPanel.add(createFieldPanel("Degree", degreeField));
        mainPanel.add(createFieldPanel("Email", emailField));
        mainPanel.add(createFieldPanel("Mobile Number", mobileField));

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        JButton cancelBtn = createButton("Cancel", AppColors.GRAY_BUTTON, Color.BLACK);
        cancelBtn.addActionListener(e -> dispose());

        JButton saveBtn = createButton("Save", AppColors.PRIMARY_PURPLE, Color.WHITE);
        saveBtn.addActionListener(e -> {
            isSaved = true;
            dispose();
        });

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);

        mainPanel.add(btnPanel);

        // Header with Title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.PRIMARY_PURPLE);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public Object[] getData() {
        return new Object[] {
                nameField.getText(),
                idField.getText(),
                degreeField.getText(),
                emailField.getText(),
                mobileField.getText()
        };
    }

    private JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(AppColors.DARK_PURPLE);
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRAY_BUTTON),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 35));
        return btn;
    }
}

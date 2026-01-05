package view;

import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DepartmentFormDialog extends JDialog {

    private boolean isSaved = false;
    private JTextField nameField;
    private JTextField hodField;
    private JTextField degreesField;
    private JTextField staffField;

    public DepartmentFormDialog(Frame parent, String title, Object[] initialData) {
        super(parent, title, true);
        setSize(400, 450); // 4 fields
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setLayout(new BorderLayout());

        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 400, 450, 20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 4 fields + buttons

        nameField = createStyledTextField("Department Name");
        hodField = createStyledTextField("HOD");
        degreesField = createStyledTextField("Degrees");
        staffField = createStyledTextField("No of Staff");

        if (initialData != null) {
            nameField.setText(initialData[0].toString());
            hodField.setText(initialData[1].toString());
            degreesField.setText(initialData[2].toString());
            staffField.setText(initialData[3].toString());
        }

        mainPanel.add(createFieldPanel("Department Name", nameField));
        mainPanel.add(createFieldPanel("HOD", hodField));
        mainPanel.add(createFieldPanel("Degrees", degreesField));
        mainPanel.add(createFieldPanel("No of Staff", staffField));

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
                hodField.getText(),
                degreesField.getText(),
                staffField.getText()
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

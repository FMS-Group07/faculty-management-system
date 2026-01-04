package view;

import javax.swing.*;
import java.awt.*;

public class LoginView {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton adminBtn, studentBtn, lecturerBtn;
    private JButton signInBtn;

    public LoginView() {

        frame = new JFrame("Faculty Management System");
        frame.setBounds(180,30,1000,650); // Updated size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(123,104,238));
        leftPanel.setBounds(0,0,500,650);
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(500,0,500,650);
        frame.add(rightPanel);

        JLabel logoLabel = new JLabel("\uD83C\uDF93");
        logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 180));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBounds(150, 70, 200, 180);

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Faculty Management<br>System</div></html>");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBounds(75, 250, 400, 100);

        JLabel FCTLabel = new JLabel("Faculty of Computing & Technology");
        FCTLabel.setForeground(Color.WHITE);
        FCTLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        FCTLabel.setBounds(80, 475, 400, 50);

        JLabel subtitleLabel = new JLabel("Manage your academic journey");
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 19));
        subtitleLabel.setBounds(120, 510, 400, 50);


        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        leftPanel.add(FCTLabel);
        leftPanel.add(subtitleLabel);

        // Right login panel
        //JPanel rightPanel = new JPanel();
//        rightPanel.setBackground(Color.WHITE);
//        rightPanel.setLayout(null); // We'll adjust bounds manually

        // Sign in label
        JLabel signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        signInLabel.setBounds(50, 50, 300, 40);
//        rightPanel.add(signInLabel);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        userLabel.setBounds(50, 130, 150, 25);
        rightPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 160, 250, 35);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passLabel.setBounds(50, 220, 150, 25);
        rightPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 250, 250, 35);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(passwordField);

        // Role selection
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        roleLabel.setBounds(50, 310, 150, 25);
        rightPanel.add(roleLabel);

        adminBtn = new JRadioButton("Admin");
        adminBtn.setBounds(50, 340, 100, 30);
        adminBtn.setSelected(true);
        adminBtn.setBackground(Color.WHITE);
        adminBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));

        studentBtn = new JRadioButton("Student");
        studentBtn.setBounds(160, 340, 120, 30);
        studentBtn.setBackground(Color.WHITE);
        studentBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));

        lecturerBtn = new JRadioButton("Lecturer");
        lecturerBtn.setBounds(290, 340, 120, 30);
        lecturerBtn.setBackground(Color.WHITE);
        lecturerBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminBtn);
        roleGroup.add(studentBtn);
        roleGroup.add(lecturerBtn);

        rightPanel.add(adminBtn);
        rightPanel.add(studentBtn);
        rightPanel.add(lecturerBtn);

        // Sign In button
        signInBtn = new JButton("Sign In");
        signInBtn.setBounds(50, 400, 250, 45);
        signInBtn.setBackground(new Color(123, 104, 238));
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setFocusPainted(false);
        signInBtn.setFont(new Font("SansSerif", Font.BOLD, 20));

        rightPanel.add(signInBtn);

        // Action listener
        signInBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = adminBtn.isSelected() ? "Admin" :
                    studentBtn.isSelected() ? "Student" : "Lecturer";

            JOptionPane.showMessageDialog(frame,
                    "Username: " + username + "\nPassword: " + password + "\nRole: " + role,
                    "Login Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Show GUI
    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().show());
    }
}

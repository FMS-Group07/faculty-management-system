package com.faculty.view;

import javax.swing.*;

import com.faculty.utils.AppColors;
import com.faculty.utils.AppConfig;

import java.awt.*;

public class LoginView {

    private JFrame frame;
    private JPanel rightPanel, signInPanel, signUpPanel;
    private JButton btnSignInTab, btnSignUpTab;
    private JPanel indicatorSignIn, indicatorSignUp;

    // Sign In components
    private JTextField signInUsernameField;
    private JPasswordField signInPasswordField;
    private JRadioButton signInAdmin, signInStudent, signInLecturer;
    private JButton signInBtn;

    // Sign Up components
    private JTextField signUpUsernameField;
    private JPasswordField signUpPasswordField, signUpConfirmField;
    private JRadioButton signUpAdmin, signUpStudent, signUpLecturer;
    private JButton signUpBtn;

    private final Color PURPLE = new Color(123, 104, 238);
    private final Color GREY = Color.GRAY;

    public LoginView() {
        frame = new JFrame("Faculty Management System");
        frame.setSize(AppConfig.FRAME_WIDTH, AppConfig.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(AppColors.PRIMARY_PURPLE);
        leftPanel.setBounds(0, 0, 500, 650);
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        JLabel logoLabel = new JLabel("\uD83C\uDF93");
        logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 180));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBounds(150, 70, 200, 180);

        JLabel titleLabel = new JLabel(
                "<html><div style='text-align:center;'>Faculty Management<br>System</div></html>"
        );
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titleLabel.setBounds(75, 250, 400, 100);

        JLabel fctLabel = new JLabel("Faculty of Computing & Technology");
        fctLabel.setForeground(Color.WHITE);
        fctLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        fctLabel.setBounds(80, 475, 400, 40);

        JLabel subtitleLabel = new JLabel("Manage your academic journey");
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 19));
        subtitleLabel.setBounds(120, 510, 400, 40);

        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        leftPanel.add(fctLabel);
        leftPanel.add(subtitleLabel);

        /* ---------- RIGHT PANEL ---------- */
        rightPanel = new JPanel();
        rightPanel.setBounds(500, 0, 500, 650);
        rightPanel.setLayout(null);
        frame.add(rightPanel);

        btnSignInTab = new JButton("Sign In");
        btnSignUpTab = new JButton("Sign Up");

        btnSignInTab.setBounds(50, 15, 150, 40);
        btnSignUpTab.setBounds(300, 15, 150, 40);

        styleTabButton(btnSignInTab);
        styleTabButton(btnSignUpTab);

        indicatorSignIn = new JPanel();
        indicatorSignUp = new JPanel();

        indicatorSignIn.setBounds(50, 60, 150, 3);
        indicatorSignUp.setBounds(300, 60, 150, 3);

        rightPanel.add(btnSignInTab);
        rightPanel.add(btnSignUpTab);
        rightPanel.add(indicatorSignIn);
        rightPanel.add(indicatorSignUp);

        createSignInForm();
        createSignUpForm();

        rightPanel.add(signInPanel);
        rightPanel.add(signUpPanel);

        setActiveTab(true);

        btnSignInTab.addActionListener(e -> setActiveTab(true));
        btnSignUpTab.addActionListener(e -> setActiveTab(false));
    }

    /* ---------- SIGN IN FORM ---------- */
    private void createSignInForm() {
        signInPanel = new JPanel();
        signInPanel.setBounds(50, 100, 400, 500);
        signInPanel.setLayout(null);

        JLabel userLabel = createLabel("Username", 0, 20);
        signInUsernameField = createTextField(0, 55);

        JLabel passLabel = createLabel("Password", 0, 115);
        signInPasswordField = createPasswordField(0, 150);

        JLabel roleLabel = createLabel("Role", 0, 210);

        signInAdmin = createRadio("Admin", 0, 245);
        signInStudent = createRadio("Student", 130, 245);
        signInLecturer = createRadio("Lecturer", 260, 245);

        ButtonGroup group = new ButtonGroup();
        group.add(signInAdmin);
        group.add(signInStudent);
        group.add(signInLecturer);

        signInBtn = new JButton("Sign In");
        signInBtn.setBounds(0, 420, 400, 42);
        signInBtn.setBackground(AppColors.PRIMARY_PURPLE);
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setFocusPainted(false);

        signInPanel.add(userLabel);
        signInPanel.add(signInUsernameField);
        signInPanel.add(passLabel);
        signInPanel.add(signInPasswordField);
        signInPanel.add(roleLabel);
        signInPanel.add(signInAdmin);
        signInPanel.add(signInStudent);
        signInPanel.add(signInLecturer);
        signInPanel.add(signInBtn);
    }

    /* ---------- SIGN UP FORM ---------- */
    private void createSignUpForm() {
        signUpPanel = new JPanel();
        signUpPanel.setBounds(50, 100, 400, 500);
        signUpPanel.setLayout(null);

        JLabel userLabel = createLabel("Username", 0, 20);
        signUpUsernameField = createTextField(0, 55);

        JLabel passLabel = createLabel("Password", 0, 115);
        signUpPasswordField = createPasswordField(0, 150);

        JLabel confirmLabel = createLabel("Confirm Password", 0, 210);
        signUpConfirmField = createPasswordField(0, 245);

        JLabel roleLabel = createLabel("Role", 0, 305);

        signUpAdmin = createRadio("Admin", 0, 340);
        signUpStudent = createRadio("Student", 130, 340);
        signUpLecturer = createRadio("Lecturer", 260, 340);

        ButtonGroup group = new ButtonGroup();
        group.add(signUpAdmin);
        group.add(signUpStudent);
        group.add(signUpLecturer);

        signUpBtn = new JButton("Sign Up");
        signUpBtn.setBounds(0, 420, 400, 42);
        signUpBtn.setBackground(AppColors.PRIMARY_PURPLE);
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFocusPainted(false);

        signUpPanel.add(userLabel);
        signUpPanel.add(signUpUsernameField);
        signUpPanel.add(passLabel);
        signUpPanel.add(signUpPasswordField);
        signUpPanel.add(confirmLabel);
        signUpPanel.add(signUpConfirmField);
        signUpPanel.add(roleLabel);
        signUpPanel.add(signUpAdmin);
        signUpPanel.add(signUpStudent);
        signUpPanel.add(signUpLecturer);
        signUpPanel.add(signUpBtn);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 250, 25);
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl.setForeground(PURPLE);
        return lbl;
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 400, 38);
        tf.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        return tf;
    }

    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField pf = new JPasswordField();
        pf.setBounds(x, y, 400, 38);
        pf.setBorder(BorderFactory.createLineBorder(AppColors.PRIMARY_PURPLE, 1));
        return pf;
    }

    private JRadioButton createRadio(String text, int x, int y) {
        JRadioButton rb = new JRadioButton(text);
        rb.setBounds(x, y, 120, 25);
        rb.setFont(new Font("Times New Roman", Font.BOLD, 18));
        rb.setForeground(AppColors.PRIMARY_PURPLE);
        rb.setOpaque(false);
        return rb;
    }

    private void setActiveTab(boolean signInActive) {
        signInPanel.setVisible(signInActive);
        signUpPanel.setVisible(!signInActive);

        btnSignInTab.setForeground(signInActive ? AppColors.DARK_PURPLE : GREY);
        btnSignUpTab.setForeground(signInActive ? GREY : AppColors.DARK_PURPLE);

        indicatorSignIn.setBackground(signInActive ? AppColors.PRIMARY_PURPLE : new Color(0, 0, 0, 0));
        indicatorSignUp.setBackground(signInActive ? new Color(0, 0, 0, 0) : AppColors.PRIMARY_PURPLE);
    }

    private void styleTabButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Times New Roman", Font.BOLD, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(GREY);
    }

    public void show() {
        frame.setVisible(true);
    }

    /* ---------- GETTERS FOR CONTROLLER ---------- */
    public String getSignInUsername() { return signInUsernameField.getText().trim(); }
    public String getSignInPassword() { return new String(signInPasswordField.getPassword()); }
    public String getSignInRole() {
        if(signInAdmin.isSelected()) return "ADMIN";
        if(signInStudent.isSelected()) return "STUDENT";
        if(signInLecturer.isSelected()) return "LECTURER";
        return "";
    }

    public String getSignUpUsername() { return signUpUsernameField.getText().trim(); }
    public String getSignUpPassword() { return new String(signUpPasswordField.getPassword()); }
    public String getSignUpConfirm() { return new String(signUpConfirmField.getPassword()); }
    public String getSignUpRole() {
        if(signUpAdmin.isSelected()) return "ADMIN";
        if(signUpStudent.isSelected()) return "STUDENT";
        if(signUpLecturer.isSelected()) return "LECTURER";
        return "";
    }

    public JButton getSignInButton() { return signInBtn; }
    public JButton getSignUpButton() { return signUpBtn; }

    public void switchToSignInTab() { setActiveTab(true); }
    public void switchToSignUpTab() { setActiveTab(false); }
}

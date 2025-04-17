import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backToLoginButton;

    public RegistrationForm() {
        setTitle("Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 245, 250), 0, getHeight(), new Color(235, 235, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Registration panel
        JPanel registrationPanel = new JPanel(new GridBagLayout());
        registrationPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(250, 30));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 30));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(250, 30));
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Buttons
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBackground(new Color(40, 167, 69));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 35));
        
        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backToLoginButton.setBackground(new Color(108, 117, 125));
        backToLoginButton.setForeground(Color.WHITE);
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setPreferredSize(new Dimension(120, 35));
        
        // Add components to registration panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        registrationPanel.add(usernameLabel, gbc);
        
        gbc.gridy = 1;
        registrationPanel.add(usernameField, gbc);
        
        gbc.gridy = 2;
        registrationPanel.add(passwordLabel, gbc);
        
        gbc.gridy = 3;
        registrationPanel.add(passwordField, gbc);
        
        gbc.gridy = 4;
        registrationPanel.add(confirmPasswordLabel, gbc);
        
        gbc.gridy = 5;
        registrationPanel.add(confirmPasswordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(backToLoginButton);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 10, 5, 10);
        registrationPanel.add(buttonPanel, gbc);

        // Add action listeners
        registerButton.addActionListener(e -> handleRegistration());
        backToLoginButton.addActionListener(e -> backToLogin());
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(registrationPanel, BorderLayout.CENTER);
        
        // Add footer
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("© 2023 Property Management System");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add your registration logic here
        JOptionPane.showMessageDialog(this, "Registration Successful!");
        backToLogin();
    }

    private void backToLogin() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
} 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientProfileFrame extends JFrame {
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField cityField;
    private JTextField addressField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton saveProfileButton;
    private JButton savePasswordButton;
    private JButton backButton;
    
    // Sample client data (in a real app, this would be fetched from a database)
    private String lastName = "Dubois";
    private String firstName = "Jean";
    private String email = "jean.dubois@example.com";
    private String phone = "06 12 34 56 78";
    private String city = "Paris";
    private String address = "123 Avenue des Champs-Élysées";
    
    public ClientProfileFrame() {
        setTitle("My Profile");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with gradient
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(230, 238, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorderPainted(false);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel (using JTabbedPane)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Profile tab
        JPanel profilePanel = new JPanel(new BorderLayout(20, 20));
        profilePanel.setBackground(new Color(255, 255, 255, 220));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Profile image
        JPanel profileImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        profileImagePanel.setOpaque(false);
        
        JLabel profileImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw a circle
                g2d.setColor(new Color(200, 200, 210));
                g2d.fillOval(0, 0, 120, 120);
                
                // Draw person silhouette
                g2d.setColor(new Color(240, 240, 245));
                g2d.fillOval(40, 20, 40, 40); // head
                g2d.fillRect(35, 62, 50, 60); // body
            }
        };
        profileImageLabel.setPreferredSize(new Dimension(120, 120));
        profileImagePanel.add(profileImageLabel);
        
        JLabel nameLabel = new JLabel(firstName + " " + lastName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(50, 50, 100));
        profileImagePanel.add(nameLabel);
        
        JLabel memberSinceLabel = new JLabel("Member since: January 2023");
        memberSinceLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        memberSinceLabel.setForeground(new Color(100, 100, 150));
        profileImagePanel.add(memberSinceLabel);
        
        profilePanel.add(profileImagePanel, BorderLayout.NORTH);
        
        // Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 20, 15));
        formPanel.setOpaque(false);
        
        // First name
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(firstNameLabel);
        
        firstNameField = new JTextField(firstName);
        firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(firstNameField);
        
        // Last name
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lastNameLabel);
        
        lastNameField = new JTextField(lastName);
        lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lastNameField);
        
        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(emailLabel);
        
        emailField = new JTextField(email);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(emailField);
        
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(phoneLabel);
        
        phoneField = new JTextField(phone);
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(phoneField);
        
        // City
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(cityLabel);
        
        cityField = new JTextField(city);
        cityField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(cityField);
        
        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(addressLabel);
        
        addressField = new JTextField(address);
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(addressField);
        
        JPanel formContainerPanel = new JPanel(new BorderLayout());
        formContainerPanel.setOpaque(false);
        formContainerPanel.add(formPanel, BorderLayout.NORTH);
        
        // Save button for profile
        JPanel saveProfilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveProfilePanel.setOpaque(false);
        
        saveProfileButton = new JButton("Save Changes");
        saveProfileButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        saveProfileButton.setBackground(new Color(13, 110, 253));
        saveProfileButton.setForeground(Color.WHITE);
        saveProfileButton.setFocusPainted(false);
        saveProfileButton.setBorderPainted(false);
        saveProfileButton.addActionListener(e -> saveProfileChanges());
        saveProfilePanel.add(saveProfileButton);
        
        formContainerPanel.add(saveProfilePanel, BorderLayout.SOUTH);
        profilePanel.add(formContainerPanel, BorderLayout.CENTER);
        
        // Security tab
        JPanel securityPanel = new JPanel(new BorderLayout(20, 20));
        securityPanel.setBackground(new Color(255, 255, 255, 220));
        securityPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel passwordFormPanel = new JPanel(new GridLayout(3, 2, 20, 15));
        passwordFormPanel.setOpaque(false);
        
        // Current password
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(currentPasswordLabel);
        
        currentPasswordField = new JPasswordField();
        currentPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(currentPasswordField);
        
        // New password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(newPasswordLabel);
        
        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(newPasswordField);
        
        // Confirm password
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(confirmPasswordLabel);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordFormPanel.add(confirmPasswordField);
        
        JPanel passwordFormContainerPanel = new JPanel(new BorderLayout());
        passwordFormContainerPanel.setOpaque(false);
        
        // Password rules message
        JPanel passwordRulesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordRulesPanel.setOpaque(false);
        
        JLabel passwordRulesLabel = new JLabel("<html><body><p style='font-size:12px;color:#666666;'>" +
                "Password must be at least 8 characters long and include: <br>" +
                "- At least one uppercase letter <br>" +
                "- At least one lowercase letter <br>" +
                "- At least one number <br>" +
                "- At least one special character</p></body></html>");
        passwordRulesPanel.add(passwordRulesLabel);
        
        passwordFormContainerPanel.add(passwordRulesPanel, BorderLayout.NORTH);
        passwordFormContainerPanel.add(passwordFormPanel, BorderLayout.CENTER);
        
        // Save button for password
        JPanel savePasswordPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        savePasswordPanel.setOpaque(false);
        
        savePasswordButton = new JButton("Update Password");
        savePasswordButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        savePasswordButton.setBackground(new Color(13, 110, 253));
        savePasswordButton.setForeground(Color.WHITE);
        savePasswordButton.setFocusPainted(false);
        savePasswordButton.setBorderPainted(false);
        savePasswordButton.addActionListener(e -> savePasswordChanges());
        savePasswordPanel.add(savePasswordButton);
        
        passwordFormContainerPanel.add(savePasswordPanel, BorderLayout.SOUTH);
        securityPanel.add(passwordFormContainerPanel, BorderLayout.NORTH);
        
        // Add tabs to the tabbed pane
        tabbedPane.addTab("Profile Information", profilePanel);
        tabbedPane.addTab("Security", securityPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add event listeners
        backButton.addActionListener(e -> backToDashboard());
        
        add(mainPanel);
    }
    
    private void saveProfileChanges() {
        // Validate inputs
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty() ||
            cityField.getText().trim().isEmpty() ||
            addressField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "All fields are required.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!emailField.getText().matches(emailPattern)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update client data
        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();
        email = emailField.getText().trim();
        phone = phoneField.getText().trim();
        city = cityField.getText().trim();
        address = addressField.getText().trim();
        
        // Show success message
        JOptionPane.showMessageDialog(this,
            "Profile information updated successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void savePasswordChanges() {
        // Get passwords
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "All password fields are required.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if current password is correct (for demo, use "password123")
        if (!currentPassword.equals("password123")) {
            JOptionPane.showMessageDialog(this,
                "The current password is incorrect.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "The new passwords do not match.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate password strength
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            JOptionPane.showMessageDialog(this,
                "Password does not meet the requirements. Please check the guidelines.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Clear password fields
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
        
        // Show success message
        JOptionPane.showMessageDialog(this,
            "Password updated successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void backToDashboard() {
        ClientDashboard dashboard = new ClientDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        // Pour tester
        SwingUtilities.invokeLater(() -> {
            ClientProfileFrame frame = new ClientProfileFrame();
            frame.setVisible(true);
        });
    }
} 
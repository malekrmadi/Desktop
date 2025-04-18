import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private HashMap<String, String[]> clientsDatabase = new HashMap<>();
    
    public LoginFrame() {
        // Initialize sample client data
        initializeClientData();
        
        // Set up the frame
        setTitle("Apartment Rental System - Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with gradient background
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(225, 235, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Set up the content
        setupUI();
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void initializeClientData() {
        // Sample client data with username and password - in a real app, passwords would be hashed
        // Format: username, password, clientId
        clientsDatabase.put("jean.dubois", new String[]{"password123", "C001"});
        clientsDatabase.put("sophie.martin", new String[]{"password123", "C002"});
        clientsDatabase.put("thomas.leroy", new String[]{"password123", "C003"});
        clientsDatabase.put("marie.petit", new String[]{"password123", "C004"});
        
        // Admin account
        clientsDatabase.put("admin", new String[]{"admin123", "A001"});
    }
    
    private void setupUI() {
        // Main content panel with some padding
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(50, 80, 50, 80));
        
        // Logo and welcome panel (top)
        JPanel headerPanel = new JPanel(new BorderLayout(0, 15));
        headerPanel.setOpaque(false);
        
        // App logo/title
        JLabel titleLabel = new JLabel("Apartment Rental System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome back! Please login to your account.");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Login form panel (center)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30, 100, 30, 100));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(70, 130, 180));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(70, 130, 180));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Remember me checkbox
        JCheckBox rememberMeCheckbox = new JCheckBox("Remember me");
        rememberMeCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rememberMeCheckbox.setForeground(new Color(100, 100, 100));
        rememberMeCheckbox.setOpaque(false);
        rememberMeCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add hover effect to button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(70, 130, 180).darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(70, 130, 180));
            }
        });
        
        // Add action listener to login button
        loginButton.addActionListener(e -> handleLogin());
        
        // Add action listener to password field (to login when pressing Enter)
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        
        // Add components to form panel with spacing
        formPanel.add(usernameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(rememberMeCheckbox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(loginButton);
        
        // Demo credentials panel (bottom)
        JPanel demoPanel = new JPanel();
        demoPanel.setOpaque(false);
        demoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel demoLabel = new JLabel("Demo credentials: username = jean.dubois, password = password123");
        demoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        demoLabel.setForeground(new Color(150, 150, 150));
        
        demoPanel.add(demoLabel);
        
        // Add all panels to content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(demoPanel, BorderLayout.SOUTH);
        
        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("Please enter both username and password.");
            return;
        }
        
        // Check credentials
        if (clientsDatabase.containsKey(username)) {
            String[] userData = clientsDatabase.get(username);
            if (userData[0].equals(password)) {
                String clientId = userData[1];
                
                // Check if admin account
                if (username.equals("admin")) {
                    // Open admin dashboard
                    try {
                        // Try to instantiate the Dashboard class (admin dashboard)
                        Dashboard dashboard = new Dashboard();
                        dashboard.setVisible(true);
                        this.dispose();
                    } catch (Exception e) {
                        // If the Dashboard class isn't found or can't be instantiated,
                        // show a message instead
                        JOptionPane.showMessageDialog(this,
                            "Admin dashboard would open here. Not implemented in this demo.",
                            "Admin Login Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Open client dashboard
                    ClientDashboard dashboard = new ClientDashboard(clientId);
                    dashboard.setVisible(true);
                    this.dispose();
                }
            } else {
                showErrorMessage("Invalid password. Please try again.");
            }
        } else {
            showErrorMessage("Username not found. Please try again.");
        }
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Login Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Display splash screen with app info
        JOptionPane.showMessageDialog(null, 
            "Welcome to the Apartment Rental System!\n\n" +
            "This application demonstrates a Java Swing-based\n" +
            "rental management system with different user interfaces\n" +
            "for clients and administrators.\n\n" +
            "Sample login credentials:\n" +
            "Client: jean.dubois / password123\n" +
            "Admin: admin / admin123",
            "Apartment Rental System",
            JOptionPane.INFORMATION_MESSAGE);
        
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
} 
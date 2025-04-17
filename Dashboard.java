import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private JButton listApartmentsButton;
    private JButton listClientsButton;
    private JButton listRentalsButton;
    private JButton logoutButton;

    public Dashboard() {
        setTitle("Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 245));

        // Create welcome label
        JLabel welcomeLabel = new JLabel("Property Management System");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Create logout button (small and in the corner)
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorderPainted(false);
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        logoutPanel.setBackground(new Color(240, 240, 245));
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 245));

        // Initialize buttons
        listApartmentsButton = new JButton("Apartments List");
        listClientsButton = new JButton("Clients List");
        listRentalsButton = new JButton("Rentals List");

        // Style buttons
        styleMainButton(listApartmentsButton, new Color(0, 123, 255));
        styleMainButton(listClientsButton, new Color(40, 167, 69));
        styleMainButton(listRentalsButton, new Color(255, 193, 7));

        // Add buttons to panel
        buttonPanel.add(listApartmentsButton);
        buttonPanel.add(listClientsButton);
        buttonPanel.add(listRentalsButton);

        // Create card panel for the buttons (to center them and add padding)
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(new Color(240, 240, 245));
        cardPanel.add(buttonPanel);
        
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // Add action listeners
        listApartmentsButton.addActionListener(e -> openApartmentsList());
        listClientsButton.addActionListener(e -> openClientsList());
        listRentalsButton.addActionListener(e -> openLocationsList());
        logoutButton.addActionListener(e -> handleLogout());

        add(mainPanel);
    }

    private void styleMainButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(250, 60));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void openApartmentsList() {
        ApartmentsListFrame apartmentsFrame = new ApartmentsListFrame();
        apartmentsFrame.setVisible(true);
        this.setVisible(false);
    }

    private void openClientsList() {
        ClientsListFrame clientsFrame = new ClientsListFrame();
        clientsFrame.setVisible(true);
        this.setVisible(false);
    }

    private void openLocationsList() {
        LocationsListFrame locationsFrame = new LocationsListFrame();
        locationsFrame.setVisible(true);
        this.setVisible(false);
    }

    private void handleLogout() {
        int response = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
            
        if (response == JOptionPane.YES_OPTION) {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            this.dispose();
        }
    }
} 
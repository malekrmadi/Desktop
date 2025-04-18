import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class ClientDashboard extends JFrame {
    private JPanel mainPanel;
    private JTable reservationsTable;
    private DefaultTableModel reservationsModel;
    private JPanel welcomePanel;
    
    public ClientDashboard() {
        setTitle("Client Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with simple background
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        setupHeader();
        setupContentPanel();
        
        add(mainPanel);
    }
    
    private void setupHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, Jean Dubois");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(70, 130, 180));
        
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setForeground(new Color(70, 130, 180));
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void setupContentPanel() {
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        contentPanel.setOpaque(false);
        
        // Boutons principaux simplifiés (3 au lieu de 4)
        JButton findApartmentBtn = new JButton("Find Your Perfect Apartment");
        findApartmentBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        findApartmentBtn.setBackground(new Color(70, 130, 180));
        findApartmentBtn.setForeground(Color.WHITE);
        findApartmentBtn.setPreferredSize(new Dimension(300, 80));
        findApartmentBtn.addActionListener(e -> openApartmentSearch());
        
        JButton myBookingsBtn = new JButton("My Bookings");
        myBookingsBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        myBookingsBtn.setBackground(new Color(70, 130, 180));
        myBookingsBtn.setForeground(Color.WHITE);
        myBookingsBtn.setPreferredSize(new Dimension(300, 80));
        myBookingsBtn.addActionListener(e -> openMyBookings());
        
        JButton myProfileBtn = new JButton("My Profile");
        myProfileBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        myProfileBtn.setBackground(new Color(70, 130, 180));
        myProfileBtn.setForeground(Color.WHITE);
        myProfileBtn.setPreferredSize(new Dimension(300, 80));
        myProfileBtn.addActionListener(e -> openMyProfile());
        
        // Centrer les boutons
        JPanel findApartmentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        findApartmentPanel.setOpaque(false);
        findApartmentPanel.add(findApartmentBtn);
        
        JPanel myBookingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        myBookingsPanel.setOpaque(false);
        myBookingsPanel.add(myBookingsBtn);
        
        JPanel myProfilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        myProfilePanel.setOpaque(false);
        myProfilePanel.add(myProfileBtn);
        
        contentPanel.add(findApartmentPanel);
        contentPanel.add(myBookingsPanel);
        contentPanel.add(myProfilePanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private void openApartmentSearch() {
        ApartmentSearchFrame searchFrame = new ApartmentSearchFrame();
        searchFrame.setVisible(true);
        this.dispose();
    }
    
    private void openMyBookings() {
        ClientRentalsFrame rentalsFrame = new ClientRentalsFrame();
        rentalsFrame.setVisible(true);
        this.dispose();
    }
    
    private void openMyProfile() {
        ClientProfileFrame profileFrame = new ClientProfileFrame();
        profileFrame.setVisible(true);
        this.dispose();
    }
    
    private void logout() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        // Pour tester
        SwingUtilities.invokeLater(() -> {
            ClientDashboard dashboard = new ClientDashboard();
            dashboard.setVisible(true);
        });
    }
} 
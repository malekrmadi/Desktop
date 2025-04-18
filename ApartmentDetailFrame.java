import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.beans.PropertyChangeListener;

public class ApartmentDetailFrame extends JFrame {
    private String clientId;
    private String apartmentId;
    private JPanel mainPanel;
    private JPanel imagePanel;
    private JLabel apartmentImage;
    private JPanel detailsPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JSpinner guestsSpinner;
    private JDateChooser checkInDate;
    private JDateChooser checkOutDate;
    private JLabel totalPriceLabel;
    private double pricePerNight;
    
    // Sample apartment data (in a real app, this would come from a database)
    private HashMap<String, Object[]> apartmentsDatabase = new HashMap<>();
    
    public ApartmentDetailFrame(String clientId, String apartmentId) {
        this.clientId = clientId;
        this.apartmentId = apartmentId;
        
        setTitle("Apartment Details");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize sample data
        initializeApartmentData();
        
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
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        setupHeader();
        setupContentPanel();
        
        add(mainPanel);
    }
    
    private void initializeApartmentData() {
        // Sample apartment data
        apartmentsDatabase.put("AP001", new Object[]{
            "Luxury Apartment in Central Paris", 
            "Paris", 
            2, 
            150.0, 
            "Available",
            new String[]{"Wi-Fi", "Air Conditioning", "Parking"},
            "Elegant 2-bedroom apartment in the heart of Paris with stunning views of the Eiffel Tower.",
            "This beautifully renovated apartment is located in the 7th arrondissement of Paris, just a 10-minute walk from the Eiffel Tower. The apartment features a spacious living room with high ceilings and large windows offering breathtaking views of the city.\n\n" +
            "The master bedroom has a comfortable queen-size bed and an en-suite bathroom with a shower. The second bedroom has two single beds ideal for children or additional guests. The fully equipped kitchen includes modern appliances, and the dining area seats up to 6 people.\n\n" +
            "Amenities include high-speed Wi-Fi, air conditioning, a flat-screen TV, a washing machine, and a dishwasher. The apartment also comes with a secure parking space, which is rare in central Paris.\n\n" +
            "The neighborhood is safe and vibrant, with numerous cafes, restaurants, and shops within walking distance. Public transportation is easily accessible, with the nearest metro station just 3 minutes away."
        });
        
        apartmentsDatabase.put("AP002", new Object[]{
            "Cozy Studio Apartment in Lyon", 
            "Lyon", 
            0, 
            75.0, 
            "Available",
            new String[]{"Wi-Fi", "Parking"},
            "Charming studio in Lyon's historic district, perfect for solo travelers or couples.",
            "This charming studio apartment is located in the heart of Lyon's historic Vieux Lyon district, a UNESCO World Heritage site. The apartment is on the second floor of a beautifully preserved 17th-century building with authentic stone walls and wooden beams.\n\n" +
            "The studio is efficiently designed with a comfortable double bed, a sitting area with a sofa and coffee table, and a dining nook for two. The kitchenette is equipped with a microwave, stovetop, refrigerator, and coffee maker. The bathroom features a shower, toilet, and sink.\n\n" +
            "Amenities include high-speed Wi-Fi, a flat-screen TV, heating, and a washing machine. The apartment comes with a parking space in a nearby secured garage.\n\n" +
            "The neighborhood is perfect for exploring Lyon's rich history and gastronomy, with numerous restaurants, cafes, and shops just steps away. The famous Saint-Jean Cathedral is a 5-minute walk, and the Fourvière Basilica is accessible via a short funicular ride."
        });
        
        apartmentsDatabase.put("AP003", new Object[]{
            "Spacious Family House in Nice", 
            "Nice", 
            3, 
            200.0, 
            "Available",
            new String[]{"Wi-Fi", "Pool", "Parking", "Air Conditioning"},
            "Beautiful 3-bedroom villa with private pool and garden, minutes from the beach.",
            "This gorgeous villa in Nice offers the perfect family getaway on the French Riviera. Located in a quiet residential area just 10 minutes from the famous Promenade des Anglais and beaches, this spacious property combines convenience with luxury and privacy.\n\n" +
            "The villa features three bedrooms: a master bedroom with a king-size bed and en-suite bathroom, a second bedroom with a queen-size bed, and a third bedroom with two single beds ideal for children. There are two additional bathrooms, one with a bathtub and one with a shower.\n\n" +
            "The large living room opens onto a terrace and garden with a private swimming pool. The fully equipped kitchen includes modern appliances, and the dining area seats up to 8 people. The outdoor space also features a BBQ area and outdoor dining furniture.\n\n" +
            "Amenities include air conditioning throughout, high-speed Wi-Fi, a flat-screen TV, a washing machine, and a dryer. The property includes secure parking for two cars within the gated grounds.\n\n" +
            "The neighborhood offers easy access to shops, restaurants, and public transportation. The Nice Côte d'Azur Airport is just a 15-minute drive away."
        });
        
        apartmentsDatabase.put("AP004", new Object[]{
            "Modern Loft in Marseille", 
            "Marseille", 
            1, 
            120.0, 
            "Available",
            new String[]{"Wi-Fi", "Air Conditioning"},
            "Contemporary loft with sea views and modern amenities in Marseille's vibrant port area.",
            "This stylish and modern loft apartment is located in Marseille's trendy Joliette district, part of the redeveloped port area. The loft is on the top floor of a converted warehouse building and offers stunning views of the Mediterranean Sea and the city.\n\n" +
            "The open-plan space features high ceilings, large windows, and industrial design elements. The bedroom area has a comfortable queen-size bed and is separated from the living area by a decorative partition. The living area includes a sofa bed that can accommodate two additional guests if needed.\n\n" +
            "The fully equipped kitchen has high-end appliances, including an induction cooktop, oven, refrigerator, and dishwasher. The bathroom features a walk-in rain shower, toilet, and double sinks.\n\n" +
            "Amenities include air conditioning, high-speed Wi-Fi, a smart TV, a Bluetooth sound system, and a washing machine. The building has an elevator and secure entry system.\n\n" +
            "The neighborhood is one of Marseille's most dynamic areas, with the famous Les Terrasses du Port shopping center, the MuCEM museum, and numerous restaurants and bars within walking distance. The Vieux Port (Old Port) is a 15-minute walk, and public transportation options are plentiful."
        });
        
        // More apartments data would be added for a real application
    }
    
    private void setupHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Back button
        JButton backButton = new JButton("Back to Search");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setForeground(new Color(70, 130, 180));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> returnToSearch());
        
        // Apartment title
        Object[] apartmentData = apartmentsDatabase.get(apartmentId);
        if (apartmentData != null) {
            JLabel titleLabel = new JLabel((String) apartmentData[0]);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setForeground(new Color(70, 130, 180));
            
            headerPanel.add(titleLabel, BorderLayout.CENTER);
            pricePerNight = (Double) apartmentData[3];
        } else {
            JLabel titleLabel = new JLabel("Apartment Details");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setForeground(new Color(70, 130, 180));
            
            headerPanel.add(titleLabel, BorderLayout.CENTER);
            pricePerNight = 100.0; // Default price if apartment not found
        }
        
        headerPanel.add(backButton, BorderLayout.WEST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void setupContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);
        
        // Get apartment data
        Object[] apartmentData = apartmentsDatabase.get(apartmentId);
        if (apartmentData == null) {
            JLabel errorLabel = new JLabel("Apartment not found", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            errorLabel.setForeground(Color.RED);
            contentPanel.add(errorLabel, BorderLayout.CENTER);
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            return;
        }
        
        // Left panel for image and details
        JPanel leftPanel = new JPanel(new BorderLayout(0, 20));
        leftPanel.setOpaque(false);
        
        // Image panel (placeholder for a real image)
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create a gradient for the placeholder
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180, 100), getWidth(), getHeight(), new Color(100, 160, 210, 100));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw a placeholder text
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
                String text = "Apartment Image";
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                g2d.drawString(text, (getWidth() - textWidth) / 2, getHeight() / 2);
            }
        };
        imagePanel.setPreferredSize(new Dimension(0, 300));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        
        // Details panel
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 15));
        detailsPanel.setOpaque(false);
        
        // Basic info panel
        JPanel basicInfoPanel = new JPanel(new GridLayout(4, 2, 15, 10));
        basicInfoPanel.setOpaque(false);
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Property Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        
        // Add basic info fields
        addLabelValuePair(basicInfoPanel, "Location:", (String) apartmentData[1]);
        addLabelValuePair(basicInfoPanel, "Bedrooms:", 
                          (Integer) apartmentData[2] == 0 ? "Studio" : ((Integer) apartmentData[2]).toString());
        addLabelValuePair(basicInfoPanel, "Price per Night:", String.format("€%.2f", (Double) apartmentData[3]));
        addLabelValuePair(basicInfoPanel, "Status:", (String) apartmentData[4]);
        
        // Amenities panel
        JPanel amenitiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        amenitiesPanel.setOpaque(false);
        amenitiesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Amenities",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        
        // Add amenities
        String[] amenities = (String[]) apartmentData[5];
        for (String amenity : amenities) {
            JLabel amenityLabel = new JLabel(amenity);
            amenityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            amenityLabel.setForeground(new Color(60, 60, 60));
            amenityLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            amenitiesPanel.add(amenityLabel);
        }
        
        // Description panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setOpaque(false);
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Description",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        
        JTextArea descriptionText = new JTextArea((String) apartmentData[7]);
        descriptionText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        descriptionText.setBackground(new Color(250, 250, 250));
        descriptionText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane descScrollPane = new JScrollPane(descriptionText);
        descScrollPane.setBorder(BorderFactory.createEmptyBorder());
        descScrollPane.setPreferredSize(new Dimension(0, 200));
        
        descriptionPanel.add(descScrollPane, BorderLayout.CENTER);
        
        // Add all info panels to details panel
        detailsPanel.add(basicInfoPanel, BorderLayout.NORTH);
        detailsPanel.add(amenitiesPanel, BorderLayout.CENTER);
        detailsPanel.add(descriptionPanel, BorderLayout.SOUTH);
        
        // Add image and details to left panel
        leftPanel.add(imagePanel, BorderLayout.NORTH);
        leftPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Right panel for booking form
        JPanel rightPanel = createBookingPanel();
        
        // Add both panels to content panel
        contentPanel.add(leftPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new BorderLayout(0, 15));
        bookingPanel.setOpaque(false);
        bookingPanel.setPreferredSize(new Dimension(350, 0));
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Booking form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 1, 0, 15));
        formPanel.setOpaque(false);
        
        // Title
        JLabel bookingTitle = new JLabel("Book This Property");
        bookingTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bookingTitle.setForeground(new Color(70, 130, 180));
        bookingTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add form fields
        JPanel namePanel = createFormField("Your Name:");
        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        namePanel.add(nameField);
        
        JPanel emailPanel = createFormField("Email:");
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailPanel.add(emailField);
        
        JPanel phonePanel = createFormField("Phone:");
        phoneField = new JTextField();
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phonePanel.add(phoneField);
        
        JPanel guestsPanel = createFormField("Number of Guests:");
        guestsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        guestsSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        guestsPanel.add(guestsSpinner);
        
        JPanel checkInPanel = createFormField("Check-in Date:");
        checkInDate = new JDateChooser();
        checkInDate.setDate(new Date());
        checkInDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkInDate.getDateEditor().addPropertyChangeListener(e -> updateTotalPrice());
        checkInPanel.add(checkInDate);
        
        JPanel checkOutPanel = createFormField("Check-out Date:");
        checkOutDate = new JDateChooser();
        // Set default checkout date to tomorrow
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        checkOutDate.setDate(calendar.getTime());
        checkOutDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkOutDate.getDateEditor().addPropertyChangeListener(e -> updateTotalPrice());
        checkOutPanel.add(checkOutDate);
        
        // Add fields to form
        formPanel.add(namePanel);
        formPanel.add(emailPanel);
        formPanel.add(phonePanel);
        formPanel.add(guestsPanel);
        formPanel.add(checkInPanel);
        formPanel.add(checkOutPanel);
        
        // Total price panel
        JPanel pricePanel = new JPanel(new BorderLayout(0, 10));
        pricePanel.setOpaque(false);
        pricePanel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        
        JLabel totalLabel = new JLabel("Total Price:");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        totalPriceLabel = new JLabel();
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPriceLabel.setForeground(new Color(70, 130, 180));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        updateTotalPrice();
        
        pricePanel.add(totalLabel, BorderLayout.WEST);
        pricePanel.add(totalPriceLabel, BorderLayout.EAST);
        
        // Book button
        JButton bookButton = new JButton("Book Now");
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bookButton.setBackground(new Color(70, 130, 180));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookButton.addActionListener(e -> bookProperty());
        
        // Add all components to booking panel
        bookingPanel.add(bookingTitle, BorderLayout.NORTH);
        bookingPanel.add(formPanel, BorderLayout.CENTER);
        bookingPanel.add(pricePanel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        buttonPanel.add(bookButton, BorderLayout.CENTER);
        
        bookingPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return bookingPanel;
    }
    
    private JPanel createFormField(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(80, 80, 80));
        
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }
    
    private void addLabelValuePair(JPanel panel, String labelText, String valueText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(80, 80, 80));
        
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        value.setForeground(new Color(60, 60, 60));
        
        panel.add(label);
        panel.add(value);
    }
    
    private void updateTotalPrice() {
        if (checkInDate.getDate() != null && checkOutDate.getDate() != null) {
            long diffInMillies = checkOutDate.getDate().getTime() - checkInDate.getDate().getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            if (diffInDays < 0) {
                totalPriceLabel.setText("Invalid dates");
                return;
            }
            
            if (diffInDays == 0) {
                diffInDays = 1; // Minimum 1 day
            }
            
            double totalPrice = pricePerNight * diffInDays;
            totalPriceLabel.setText(String.format("€%.2f", totalPrice));
        } else {
            totalPriceLabel.setText("€0.00");
        }
    }
    
    private void bookProperty() {
        // Validate form
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Form Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (checkInDate.getDate() == null || checkOutDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select valid dates.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (checkOutDate.getDate().before(checkInDate.getDate())) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Calculate booking details
        long diffInMillies = checkOutDate.getDate().getTime() - checkInDate.getDate().getTime();
        long nights = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (nights == 0) nights = 1;
        
        double totalPrice = pricePerNight * nights;
        
        // Format dates for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String checkInStr = dateFormat.format(checkInDate.getDate());
        String checkOutStr = dateFormat.format(checkOutDate.getDate());
        
        // In a real application, this would save the booking to a database
        // For now, we'll just show a confirmation message
        StringBuilder message = new StringBuilder();
        message.append("Booking Confirmation\n\n");
        message.append("Property: ").append(apartmentsDatabase.get(apartmentId)[0]).append("\n");
        message.append("Guest: ").append(nameField.getText()).append("\n");
        message.append("Check-in: ").append(checkInStr).append("\n");
        message.append("Check-out: ").append(checkOutStr).append("\n");
        message.append("Nights: ").append(nights).append("\n");
        message.append("Guests: ").append(guestsSpinner.getValue()).append("\n");
        message.append("Total Price: €").append(String.format("%.2f", totalPrice)).append("\n\n");
        message.append("Thank you for your booking! A confirmation email has been sent to ").append(emailField.getText());
        
        JOptionPane.showMessageDialog(this, message.toString(), "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        
        // Return to client dashboard
        ClientDashboard dashboard = new ClientDashboard(clientId);
        dashboard.setVisible(true);
        this.dispose();
    }
    
    private void returnToSearch() {
        ApartmentSearchFrame searchFrame = new ApartmentSearchFrame(clientId);
        searchFrame.setVisible(true);
        this.dispose();
    }
    
    // Custom date chooser component
    private class JDateChooser extends JPanel {
        private JTextField dateField;
        private Date date;
        private Calendar calendar;
        private JButton dateButton;
        private DateEditor dateEditor;
        
        public JDateChooser() {
            setLayout(new BorderLayout(5, 0));
            
            dateField = new JTextField();
            dateField.setEditable(false);
            dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            dateButton = new JButton("...");
            dateButton.setPreferredSize(new Dimension(30, 25));
            dateButton.addActionListener(e -> openDatePicker());
            
            add(dateField, BorderLayout.CENTER);
            add(dateButton, BorderLayout.EAST);
            
            calendar = Calendar.getInstance();
            dateEditor = new DateEditor();
        }
        
        public void setDate(Date date) {
            this.date = date;
            calendar.setTime(date);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateField.setText(sdf.format(date));
            
            dateEditor.firePropertyChange();
        }
        
        public Date getDate() {
            return date;
        }
        
        public void setFont(Font font) {
            if (dateField != null) {
                dateField.setFont(font);
            }
        }
        
        public DateEditor getDateEditor() {
            return dateEditor;
        }
        
        private void openDatePicker() {
            // In a real app, this would open a proper date picker
            // Here we use a simple dialog for demonstration
            String input = JOptionPane.showInputDialog(this, 
                "Enter date (dd/MM/yyyy):", 
                "Select Date", 
                JOptionPane.PLAIN_MESSAGE);
            
            if (input != null && !input.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date newDate = sdf.parse(input);
                    setDate(newDate);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid date format. Please use dd/MM/yyyy", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        public class DateEditor {
            public void firePropertyChange() {
                JDateChooser.this.firePropertyChange("date", null, getDate());
            }
            
            public void addPropertyChangeListener(PropertyChangeListener listener) {
                JDateChooser.this.addPropertyChangeListener(listener);
            }
        }
    }
    
    public static void main(String[] args) {
        // For testing purposes
        SwingUtilities.invokeLater(() -> {
            ApartmentDetailFrame detailFrame = new ApartmentDetailFrame("C001", "AP001");
            detailFrame.setVisible(true);
        });
    }
}
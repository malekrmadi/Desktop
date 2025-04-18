import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.beans.PropertyChangeListener;

public class ApartmentSearchFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField locationField;
    private JComboBox<String> priceRangeBox;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private HashMap<String, Object[]> apartmentsDatabase = new HashMap<>();
    
    public ApartmentSearchFrame() {
        setTitle("Search Apartments");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize sample data
        initializeApartmentData();
        
        // Create main panel with simple background
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        setupHeader();
        setupSearchPanel();
        setupResultsPanel();
        
        add(mainPanel);
    }
    
    private void initializeApartmentData() {
        // Sample apartment data
        apartmentsDatabase.put("AP001", new Object[]{
            "Luxury Apartment in Central Paris", 
            "Paris", 
            2, 
            150.0, 
            "Available"
        });
        
        apartmentsDatabase.put("AP002", new Object[]{
            "Cozy Studio Apartment in Lyon", 
            "Lyon", 
            0, 
            75.0, 
            "Available"
        });
        
        apartmentsDatabase.put("AP003", new Object[]{
            "Spacious Family House in Nice", 
            "Nice", 
            3, 
            200.0, 
            "Available"
        });
        
        apartmentsDatabase.put("AP004", new Object[]{
            "Modern Loft in Marseille", 
            "Marseille", 
            1, 
            120.0, 
            "Available"
        });
    }
    
    private void setupHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> returnToDashboard());
        
        // Title
        JLabel titleLabel = new JLabel("Find Your Perfect Apartment");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void setupSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Location field
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        locationField = new JTextField(15);
        
        // Price range dropdown
        JLabel priceLabel = new JLabel("Max Price:");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceRangeBox = new JComboBox<>(new String[]{"Any", "€100", "€200", "€300", "€400", "€500"});
        
        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchApartments());
        
        searchPanel.add(locationLabel);
        searchPanel.add(locationField);
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(priceLabel);
        searchPanel.add(priceRangeBox);
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(searchButton);
        
        mainPanel.add(searchPanel, BorderLayout.CENTER);
    }
    
    private void setupResultsPanel() {
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setOpaque(false);
        
        // Create table model with columns
        String[] columnNames = {"ID", "Name", "Location", "Bedrooms", "Price/Night", "Status", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only action column is editable
            }
        };
        
        // Create results table
        resultsTable = new JTable(tableModel);
        resultsTable.setRowHeight(40);
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Add view button to the Actions column
        resultsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        resultsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Add scroll pane to results panel
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load initial results
        loadAllApartments();
        
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);
    }
    
    private void searchApartments() {
        tableModel.setRowCount(0);
        
        String locationQuery = locationField.getText().trim().toLowerCase();
        double maxPrice = Double.MAX_VALUE;
        
        String priceSelection = (String) priceRangeBox.getSelectedItem();
        if (!priceSelection.equals("Any")) {
            maxPrice = Double.parseDouble(priceSelection.substring(1)); // Remove € sign
        }
        
        for (Map.Entry<String, Object[]> entry : apartmentsDatabase.entrySet()) {
            String id = entry.getKey();
            Object[] data = entry.getValue();
            
            String location = ((String) data[1]).toLowerCase();
            double price = (Double) data[3];
            
            // Filter by location and price
            if ((locationQuery.isEmpty() || location.contains(locationQuery)) && price <= maxPrice) {
                Object[] rowData = new Object[7];
                rowData[0] = id;
                rowData[1] = data[0]; // Name
                rowData[2] = data[1]; // Location
                rowData[3] = (Integer) data[2] == 0 ? "Studio" : data[2]; // Bedrooms
                rowData[4] = String.format("€%.2f", price); // Price
                rowData[5] = data[4]; // Status
                rowData[6] = "View"; // Action button text
                
                tableModel.addRow(rowData);
            }
        }
        
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No apartments found matching your criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadAllApartments() {
        tableModel.setRowCount(0);
        
        for (Map.Entry<String, Object[]> entry : apartmentsDatabase.entrySet()) {
            String id = entry.getKey();
            Object[] data = entry.getValue();
            
            Object[] rowData = new Object[7];
            rowData[0] = id;
            rowData[1] = data[0]; // Name
            rowData[2] = data[1]; // Location
            rowData[3] = (Integer) data[2] == 0 ? "Studio" : data[2]; // Bedrooms
            rowData[4] = String.format("€%.2f", (Double) data[3]); // Price
            rowData[5] = data[4]; // Status
            rowData[6] = "View"; // Action button text
            
            tableModel.addRow(rowData);
        }
    }
    
    private void viewApartment(String apartmentId) {
        JOptionPane.showMessageDialog(this, 
            "View details for apartment " + apartmentId + "\nThis functionality will be implemented later.", 
            "Apartment Details", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void returnToDashboard() {
        ClientDashboard dashboard = new ClientDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    // Button renderer for the actions column
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorderPainted(false);
            setFocusPainted(false);
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            return this;
        }
    }
    
    // Button editor for the actions column
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String buttonLabel;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            
            button = new JButton();
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            buttonLabel = (value == null) ? "" : value.toString();
            button.setText(buttonLabel);
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return buttonLabel;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
        
        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
            
            if (isPushed) {
                // Get the selected apartment ID from the table
                int selectedRow = resultsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String apartmentId = (String) resultsTable.getValueAt(selectedRow, 0);
                    viewApartment(apartmentId);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // Pour tester
        SwingUtilities.invokeLater(() -> {
            ApartmentSearchFrame searchFrame = new ApartmentSearchFrame();
            searchFrame.setVisible(true);
        });
    }
} 
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationsListFrame extends JFrame {
    private JTable rentalsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton backButton;
    private JComboBox<String> filterComboBox;
    
    // Sample data for the table
    private Object[][] rentalData = {
        {"L001", "A101", "C001", "Dupont Jean", "01/01/2023", "12/31/2023", "$800", "Active"},
        {"L002", "A102", "C002", "Martin Sophie", "03/01/2023", "02/28/2024", "$950", "Active"},
        {"L003", "A104", "C003", "Bernard Michel", "06/01/2023", "05/31/2024", "$750", "Active"},
        {"L004", "A103", "C004", "Petit Laura", "09/01/2022", "08/31/2023", "$1100", "Ended"},
        {"L005", "A105", "C005", "Robert Thomas", "12/01/2022", "11/30/2023", "$650", "Active"}
    };
    
    // Column names
    private String[] columnNames = {"ID", "Apt. ID", "Client ID", "Client Name", "Start Date", "End Date", "Rent", "Status", "Actions"};
    
    public LocationsListFrame() {
        setTitle("Rentals Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with gradient
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Rentals List");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Filter area
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setOpaque(false);
        
        JLabel filterLabel = new JLabel("Filter by status: ");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        filterComboBox = new JComboBox<>(new String[]{"All", "Active", "Ended"});
        filterComboBox.setPreferredSize(new Dimension(150, 30));
        filterComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        headerPanel.add(filterPanel, BorderLayout.CENTER);
        
        // Top buttons area
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setOpaque(false);
        
        // Add button
        addButton = new JButton("Add Rental");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorderPainted(false);
        addButton.addActionListener(e -> {
            AddLocationFrame addLocationFrame = new AddLocationFrame(this);
            addLocationFrame.setVisible(true);
        });
        
        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorderPainted(false);
        
        topButtonPanel.add(addButton);
        topButtonPanel.add(Box.createHorizontalStrut(10)); // Space between buttons
        topButtonPanel.add(backButton);
        headerPanel.add(topButtonPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Statistics panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        statsPanel.setOpaque(false);
        
        // Total rentals
        JPanel totalPanel = createStatPanel("Total rentals", String.valueOf(rentalData.length), new Color(13, 110, 253));
        
        // Active rentals (count active ones)
        int activeCount = 0;
        for (Object[] row : rentalData) {
            if (row[7].equals("Active")) activeCount++;
        }
        JPanel activePanel = createStatPanel("Active rentals", String.valueOf(activeCount), new Color(25, 135, 84));
        
        // Ended rentals
        JPanel endedPanel = createStatPanel("Ended rentals", String.valueOf(rentalData.length - activeCount), new Color(220, 53, 69));
        
        statsPanel.add(totalPanel);
        statsPanel.add(activePanel);
        statsPanel.add(endedPanel);
        
        JPanel statsContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsContainerPanel.setOpaque(false);
        statsContainerPanel.add(statsPanel);
        
        mainPanel.add(statsContainerPanel, BorderLayout.SOUTH);
        
        // Table
        tableModel = new DefaultTableModel(rentalData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only the "Actions" column is editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        
        rentalsTable = new JTable(tableModel);
        rentalsTable.setRowHeight(40);
        rentalsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rentalsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        rentalsTable.getTableHeader().setBackground(new Color(240, 240, 245));
        rentalsTable.setShowGrid(true);
        rentalsTable.setGridColor(new Color(230, 230, 230));
        
        // Conditional cell styling (for status)
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (column == 7) { // Status column
                    String status = value.toString();
                    if (status.equals("Active")) {
                        comp.setForeground(new Color(25, 135, 84)); // Green
                    } else if (status.equals("Ended")) {
                        comp.setForeground(new Color(220, 53, 69)); // Red
                    } else {
                        comp.setForeground(Color.BLACK);
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    comp.setForeground(Color.BLACK);
                    setHorizontalAlignment(JLabel.LEFT);
                }
                
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                
                return comp;
            }
        };
        
        // Apply rendering to all cells
        for (int i = 0; i < rentalsTable.getColumnCount() - 1; i++) {
            rentalsTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
        }
        
        // Make the last column editable with buttons
        rentalsTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
        rentalsTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Set column widths
        TableColumnModel columnModel = rentalsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(80); // Apt ID
        columnModel.getColumn(2).setPreferredWidth(80); // Client ID
        columnModel.getColumn(3).setPreferredWidth(150); // Client Name
        columnModel.getColumn(4).setPreferredWidth(100); // Start Date
        columnModel.getColumn(5).setPreferredWidth(100); // End Date
        columnModel.getColumn(6).setPreferredWidth(80); // Rent
        columnModel.getColumn(7).setPreferredWidth(80); // Status
        columnModel.getColumn(8).setPreferredWidth(150); // Actions
        
        JScrollPane scrollPane = new JScrollPane(rentalsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add event listeners
        backButton.addActionListener(e -> backToDashboard());
        
        // Filter listener
        filterComboBox.addActionListener(e -> filterRentals());
        
        add(mainPanel);
    }
    
    private JPanel createStatPanel(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setForeground(color);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void filterRentals() {
        String selectedFilter = (String) filterComboBox.getSelectedItem();
        
        // If "All" is selected, show all rentals
        if (selectedFilter.equals("All")) {
            tableModel.setDataVector(rentalData, columnNames);
        } else {
            // Filter according to the selected status
            java.util.List<Object[]> filteredData = new java.util.ArrayList<>();
            for (Object[] row : rentalData) {
                if (row[7].equals(selectedFilter)) {
                    filteredData.add(row);
                }
            }
            
            Object[][] filteredArray = new Object[filteredData.size()][];
            filteredArray = filteredData.toArray(filteredArray);
            
            tableModel.setDataVector(filteredArray, columnNames);
        }
        
        // Reapply the renderers and editors (they are lost after setDataVector)
        setupTableRenderers();
    }
    
    private void setupTableRenderers() {
        // Conditional cell styling (for status)
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (column == 7) { // Status column
                    String status = value.toString();
                    if (status.equals("Active")) {
                        comp.setForeground(new Color(25, 135, 84)); // Green
                    } else if (status.equals("Ended")) {
                        comp.setForeground(new Color(220, 53, 69)); // Red
                    } else {
                        comp.setForeground(Color.BLACK);
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    comp.setForeground(Color.BLACK);
                    setHorizontalAlignment(JLabel.LEFT);
                }
                
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                
                return comp;
            }
        };
        
        // Apply rendering to all cells
        for (int i = 0; i < rentalsTable.getColumnCount() - 1; i++) {
            rentalsTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
        }
        
        // Make the last column editable with buttons
        rentalsTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
        rentalsTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    private void backToDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    // Class for rendering buttons in the table
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton deleteButton;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            setOpaque(true);
            
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(255, 193, 7));
            editButton.setForeground(Color.WHITE);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setPreferredSize(new Dimension(60, 30));
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(220, 53, 69));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setPreferredSize(new Dimension(60, 30));
            
            add(editButton);
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }
    
    // Class for editing buttons in the table
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private String action = "";
        private boolean isPushed;
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(255, 193, 7));
            editButton.setForeground(Color.WHITE);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setPreferredSize(new Dimension(75, 30));
            editButton.addActionListener(e -> {
                action = "edit";
                fireEditingStopped();
            });
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(220, 53, 69));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setPreferredSize(new Dimension(75, 30));
            deleteButton.addActionListener(e -> {
                action = "delete";
                fireEditingStopped();
            });
            
            panel.add(editButton);
            panel.add(deleteButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.currentRow = row;
            isPushed = true;
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (action.equals("edit")) {
                    handleEditRental(currentRow);
                } else if (action.equals("delete")) {
                    handleDeleteRental(currentRow);
                }
            }
            isPushed = false;
            return action;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    private void handleEditRental(int row) {
        Object[] locationData = new Object[8];
        for (int i = 0; i < 8; i++) {
            locationData[i] = tableModel.getValueAt(row, i);
        }
        EditLocationFrame editFrame = new EditLocationFrame(this, row, locationData);
        editFrame.setVisible(true);
    }
    
    private void handleDeleteRental(int row) {
        String rentalId = (String) tableModel.getValueAt(row, 0);
        String clientName = (String) tableModel.getValueAt(row, 3);
        String apartmentId = (String) tableModel.getValueAt(row, 1);
        
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete rental " + rentalId + "?\n" +
                "Apartment: " + apartmentId + "\n" +
                "Client: " + clientName,
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (response == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Rental " + rentalId + " deleted successfully");
            
            // Update statistics
            updateStatistics();
        }
    }
    
    private void updateStatistics() {
        // This method would be implemented to update statistics
        // after a deletion or addition
    }
    
    public void addLocation(Object[] newLocation) {
        tableModel.addRow(newLocation);
        // Update statistics
        updateStatistics();
    }

    public void updateLocation(int row, Object[] updatedLocation) {
        // Update client name based on client ID (in a real app, this would fetch from database)
        String clientName = "Updated Client"; // This is just a placeholder
        updatedLocation[3] = clientName;
        
        for (int i = 0; i < updatedLocation.length; i++) {
            tableModel.setValueAt(updatedLocation[i], row, i);
        }
        // Update statistics
        updateStatistics();
    }
} 
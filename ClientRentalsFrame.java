import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ClientRentalsFrame extends JFrame {
    private JTable rentalsTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private JComboBox<String> filterComboBox;
    
    // Sample data for the table (in a real app, this would be fetched from a database)
    private Object[][] rentalData = {
        {"L001", "A101", "T2", "Paris", "01/01/2023", "12/31/2023", "$800", "Active"},
        {"L002", "A105", "Studio", "Paris", "03/01/2023", "02/28/2024", "$650", "Active"},
        {"L003", "A103", "T4", "Marseille", "06/01/2022", "05/31/2023", "$1100", "Completed"},
        {"L004", "A102", "T3", "Lyon", "09/01/2021", "08/31/2022", "$950", "Completed"},
        {"L005", "A104", "T2", "Bordeaux", "12/01/2020", "11/30/2021", "$750", "Completed"}
    };
    
    // Column names
    private String[] columnNames = {"Rental ID", "Apt. ID", "Type", "City", "Start Date", "End Date", "Monthly Rent", "Status"};
    
    public ClientRentalsFrame() {
        setTitle("My Rental History");
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
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(230, 238, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("My Rental History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Filter area
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setOpaque(false);
        
        JLabel filterLabel = new JLabel("Filter by status: ");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        filterComboBox = new JComboBox<>(new String[]{"All", "Active", "Completed"});
        filterComboBox.setPreferredSize(new Dimension(150, 30));
        filterComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        headerPanel.add(filterPanel, BorderLayout.CENTER);
        
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
        
        // Completed rentals
        JPanel completedPanel = createStatPanel("Completed rentals", String.valueOf(rentalData.length - activeCount), new Color(108, 117, 125));
        
        statsPanel.add(totalPanel);
        statsPanel.add(activePanel);
        statsPanel.add(completedPanel);
        
        JPanel statsContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsContainerPanel.setOpaque(false);
        statsContainerPanel.add(statsPanel);
        
        mainPanel.add(statsContainerPanel, BorderLayout.SOUTH);
        
        // Table
        tableModel = new DefaultTableModel(rentalData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable for client view
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
                    } else if (status.equals("Completed")) {
                        comp.setForeground(new Color(108, 117, 125)); // Gray
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
        for (int i = 0; i < rentalsTable.getColumnCount(); i++) {
            rentalsTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
        }
        
        // Set column widths
        TableColumnModel columnModel = rentalsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70); // Rental ID
        columnModel.getColumn(1).setPreferredWidth(70); // Apt ID
        columnModel.getColumn(2).setPreferredWidth(70); // Type
        columnModel.getColumn(3).setPreferredWidth(100); // City
        columnModel.getColumn(4).setPreferredWidth(100); // Start Date
        columnModel.getColumn(5).setPreferredWidth(100); // End Date
        columnModel.getColumn(6).setPreferredWidth(100); // Monthly Rent
        columnModel.getColumn(7).setPreferredWidth(80); // Status
        
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
        
        // Reapply the renderers (they are lost after setDataVector)
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
                    } else if (status.equals("Completed")) {
                        comp.setForeground(new Color(108, 117, 125)); // Gray
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
        for (int i = 0; i < rentalsTable.getColumnCount(); i++) {
            rentalsTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
        }
    }
    
    private void backToDashboard() {
        ClientDashboard dashboard = new ClientDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        // Pour tester
        SwingUtilities.invokeLater(() -> {
            ClientRentalsFrame frame = new ClientRentalsFrame();
            frame.setVisible(true);
        });
    }
} 
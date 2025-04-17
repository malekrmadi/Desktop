import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ApartmentsListFrame extends JFrame {
    private JTable apartmentsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton backButton;
    
    // Sample data for the table
    private Object[][] apartmentData = {
        {"A101", "T2", "Paris", "50", "$800", "Available"},
        {"A102", "T3", "Lyon", "75", "$950", "Rented"},
        {"A103", "T4", "Marseille", "100", "$1100", "Available"},
        {"A104", "T2", "Bordeaux", "55", "$750", "Rented"},
        {"A105", "Studio", "Paris", "30", "$650", "Available"}
    };
    
    // Column names
    private String[] columnNames = {"ID", "Type", "City", "Size (m²)", "Rent", "Status", "Actions"};
    
    public ApartmentsListFrame() {
        setTitle("Apartments Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 245));
        
        JLabel titleLabel = new JLabel("Apartments List");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Top buttons area
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(new Color(240, 240, 245));
        
        // Add button
        addButton = new JButton("Add Apartment");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorderPainted(false);
        addButton.addActionListener(e -> {
            AddApartmentFrame addApartmentFrame = new AddApartmentFrame(this);
            addApartmentFrame.setVisible(true);
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
        
        // Information panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(new Color(230, 240, 255));
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 240), 1));
        
        JLabel infoLabel = new JLabel("Total apartments: " + apartmentData.length);
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoPanel.add(infoLabel);
        
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // Table
        tableModel = new DefaultTableModel(apartmentData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Actions" column is editable
            }
        };
        
        apartmentsTable = new JTable(tableModel);
        apartmentsTable.setRowHeight(40);
        apartmentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        apartmentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        apartmentsTable.getTableHeader().setBackground(new Color(240, 240, 245));
        apartmentsTable.setShowGrid(true);
        apartmentsTable.setGridColor(new Color(230, 230, 230));
        
        // Alternating row colors
        apartmentsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                
                return comp;
            }
        });
        
        // Make the last column editable with buttons
        apartmentsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        apartmentsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Set column widths
        TableColumnModel columnModel = apartmentsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(80); // Type
        columnModel.getColumn(2).setPreferredWidth(100); // City
        columnModel.getColumn(3).setPreferredWidth(80); // Size
        columnModel.getColumn(4).setPreferredWidth(80); // Rent
        columnModel.getColumn(5).setPreferredWidth(100); // Status
        columnModel.getColumn(6).setPreferredWidth(150); // Actions
        
        JScrollPane scrollPane = new JScrollPane(apartmentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add event listeners
        backButton.addActionListener(e -> backToDashboard());
        
        add(mainPanel);
    }
    
    private void handleAddApartment() {
        JOptionPane.showMessageDialog(this, "Add apartment feature to be implemented");
    }
    
    private void backToDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    private void handleEditApartment(int row) {
        Object[] apartmentData = new Object[6];
        for (int i = 0; i < 6; i++) {
            apartmentData[i] = tableModel.getValueAt(row, i);
        }
        EditApartmentFrame editFrame = new EditApartmentFrame(this, row, apartmentData);
        editFrame.setVisible(true);
    }

    public void updateApartment(int row, Object[] updatedApartment) {
        for (int i = 0; i < updatedApartment.length; i++) {
            tableModel.setValueAt(updatedApartment[i], row, i);
        }
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
                    handleEditApartment(currentRow);
                } else if (action.equals("delete")) {
                    handleDeleteApartment(currentRow);
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
    
    private void handleDeleteApartment(int row) {
        String apartmentId = (String) tableModel.getValueAt(row, 0);
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete apartment " + apartmentId + "?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (response == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Apartment " + apartmentId + " deleted successfully");
        }
    }

    public void addApartment(Object[] newApartment) {
        tableModel.addRow(newApartment);
        // Update the total apartments count
        updateTotalCount();
    }

    private void updateTotalCount() {
        // Find the info panel and update the count
        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel mainPanel = (JPanel) comp;
                for (Component innerComp : mainPanel.getComponents()) {
                    if (innerComp instanceof JPanel && 
                        ((JPanel) innerComp).getBackground().equals(new Color(230, 240, 255))) {
                        JPanel infoPanel = (JPanel) innerComp;
                        JLabel infoLabel = (JLabel) infoPanel.getComponent(0);
                        infoLabel.setText("Total apartments: " + tableModel.getRowCount());
                        break;
                    }
                }
            }
        }
    }
} 
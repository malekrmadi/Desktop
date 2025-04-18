import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AdminsListFrame extends JFrame {
    private JTable adminsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton backButton;
    private JTextField searchField;
    
    // Sample data for the table
    private Object[][] adminData = {
        {"A001", "John", "Doe", "john.doe@example.com", "********", false},
        {"A002", "Jane", "Smith", "jane.smith@example.com", "********", true},
        {"A003", "Robert", "Johnson", "robert.johnson@example.com", "********", false},
        {"A004", "Emily", "Davis", "emily.davis@example.com", "********", false},
        {"A005", "Michael", "Wilson", "michael.wilson@example.com", "********", true}
    };
    
    // Column names
    private String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Password", "Super Admin", "Actions"};
    
    public AdminsListFrame() {
        setTitle("Admins Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(new Color(240, 240, 245));
        
        JLabel titleLabel = new JLabel("Administrators List");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 100));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Search area
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(240, 240, 245));
        
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        
        // Top buttons area
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(new Color(240, 240, 245));
        
        // Add button
        addButton = new JButton("Add Admin");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorderPainted(false);
        addButton.addActionListener(e -> {
            AddAdminFrame addAdminFrame = new AddAdminFrame(this);
            addAdminFrame.setVisible(true);
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
        
        JLabel infoLabel = new JLabel("Total admins: " + adminData.length);
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoPanel.add(infoLabel);
        
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // Table
        tableModel = new DefaultTableModel(adminData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Actions" column is editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) { // Super Admin column
                    return Boolean.class;
                }
                return String.class;
            }
        };
        
        adminsTable = new JTable(tableModel);
        adminsTable.setRowHeight(40);
        adminsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminsTable.getTableHeader().setBackground(new Color(240, 240, 245));
        adminsTable.setShowGrid(true);
        adminsTable.setGridColor(new Color(230, 230, 230));
        
        // Alternating row colors
        adminsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        adminsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        adminsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Set column widths
        TableColumnModel columnModel = adminsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(100); // First Name
        columnModel.getColumn(2).setPreferredWidth(100); // Last Name
        columnModel.getColumn(3).setPreferredWidth(200); // Email
        columnModel.getColumn(4).setPreferredWidth(100); // Password
        columnModel.getColumn(5).setPreferredWidth(100); // Super Admin
        columnModel.getColumn(6).setPreferredWidth(150); // Actions
        
        JScrollPane scrollPane = new JScrollPane(adminsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add event listeners
        backButton.addActionListener(e -> backToDashboard());
        
        // Real-time search
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchAdmins();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchAdmins();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchAdmins();
            }
        });
        
        add(mainPanel);
    }
    
    private void searchAdmins() {
        String searchTerm = searchField.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            tableModel.setDataVector(adminData, columnNames);
            // Reapply the renderer for the actions column
            setupTableRenderers();
            return;
        }
        
        java.util.List<Object[]> filteredData = new java.util.ArrayList<>();
        for (Object[] row : adminData) {
            for (int i = 0; i < 5; i++) { // Only search in ID, first name, last name, email (not password or super admin)
                if (row[i].toString().toLowerCase().contains(searchTerm)) {
                    filteredData.add(row);
                    break;
                }
            }
        }
        
        Object[][] filteredArray = new Object[filteredData.size()][];
        filteredArray = filteredData.toArray(filteredArray);
        
        tableModel.setDataVector(filteredArray, columnNames);
        // Reapply the renderer for the actions column
        setupTableRenderers();
    }
    
    private void setupTableRenderers() {
        // Set up boolean renderer for Super Admin column
        adminsTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(JLabel.CENTER);
            }
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                checkBox.setHorizontalAlignment(JLabel.CENTER);
                
                if (!isSelected) {
                    checkBox.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                } else {
                    checkBox.setBackground(table.getSelectionBackground());
                }
                
                return checkBox;
            }
        });
        
        // Make the last column editable with buttons
        adminsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        adminsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
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
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
            
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(255, 193, 7));
            editButton.setForeground(Color.WHITE);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setPreferredSize(new Dimension(60, 30));
            editButton.addActionListener(e -> {
                action = "edit";
                fireEditingStopped();
            });
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(220, 53, 69));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setPreferredSize(new Dimension(60, 30));
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
                    handleEditAdmin(currentRow);
                } else if (action.equals("delete")) {
                    handleDeleteAdmin(currentRow);
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
    
    private void handleEditAdmin(int row) {
        Object[] adminData = new Object[6];
        for (int i = 0; i < 6; i++) {
            adminData[i] = tableModel.getValueAt(row, i);
        }
        EditAdminFrame editFrame = new EditAdminFrame(this, row, adminData);
        editFrame.setVisible(true);
    }
    
    private void handleDeleteAdmin(int row) {
        String adminId = (String) tableModel.getValueAt(row, 0);
        String adminName = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);
        
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete admin " + adminName + " (ID: " + adminId + ")?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (response == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            updateTotalCount();
            JOptionPane.showMessageDialog(this, "Admin " + adminName + " deleted successfully");
        }
    }
    
    public void addAdmin(Object[] newAdmin) {
        tableModel.addRow(newAdmin);
        updateTotalCount();
    }
    
    public void updateAdmin(int row, Object[] updatedAdmin) {
        for (int i = 0; i < updatedAdmin.length; i++) {
            tableModel.setValueAt(updatedAdmin[i], row, i);
        }
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
                        infoLabel.setText("Total admins: " + tableModel.getRowCount());
                        break;
                    }
                }
            }
        }
    }
} 
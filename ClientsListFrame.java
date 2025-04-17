import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ClientsListFrame extends JFrame {
    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton backButton;
    private JTextField searchField;
    
    // Sample data for the table
    private Object[][] clientData = {
        {"C001", "Dupont", "Jean", "0612345678", "jean.dupont@email.com", "Paris"},
        {"C002", "Martin", "Sophie", "0623456789", "sophie.martin@email.com", "Lyon"},
        {"C003", "Bernard", "Michel", "0634567890", "michel.bernard@email.com", "Marseille"},
        {"C004", "Petit", "Laura", "0645678901", "laura.petit@email.com", "Bordeaux"},
        {"C005", "Robert", "Thomas", "0656789012", "thomas.robert@email.com", "Paris"}
    };
    
    // Column names
    private String[] columnNames = {"ID", "Last Name", "First Name", "Phone", "Email", "City", "Actions"};
    
    public ClientsListFrame() {
        setTitle("Clients Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(new Color(240, 240, 245));
        
        JLabel titleLabel = new JLabel("Clients List");
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
        addButton = new JButton("Add Client");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorderPainted(false);
        
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
        
        JLabel infoLabel = new JLabel("Total clients: " + clientData.length);
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoPanel.add(infoLabel);
        
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // Table
        tableModel = new DefaultTableModel(clientData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Actions" column is editable
            }
        };
        
        clientsTable = new JTable(tableModel);
        clientsTable.setRowHeight(40);
        clientsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clientsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        clientsTable.getTableHeader().setBackground(new Color(240, 240, 245));
        clientsTable.setShowGrid(true);
        clientsTable.setGridColor(new Color(230, 230, 230));
        
        // Alternating row colors
        clientsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        clientsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        clientsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Set column widths
        TableColumnModel columnModel = clientsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(100); // Last Name
        columnModel.getColumn(2).setPreferredWidth(100); // First Name
        columnModel.getColumn(3).setPreferredWidth(100); // Phone
        columnModel.getColumn(4).setPreferredWidth(200); // Email
        columnModel.getColumn(5).setPreferredWidth(100); // City
        columnModel.getColumn(6).setPreferredWidth(150); // Actions
        
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add event listeners
        addButton.addActionListener(e -> handleAddClient());
        backButton.addActionListener(e -> backToDashboard());
        
        // Real-time search
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchClients();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchClients();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchClients();
            }
        });
        
        add(mainPanel);
    }
    
    private void searchClients() {
        String searchTerm = searchField.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            tableModel.setDataVector(clientData, columnNames);
            // Reapply the renderer for the actions column
            clientsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
            clientsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
            return;
        }
        
        java.util.List<Object[]> filteredData = new java.util.ArrayList<>();
        for (Object[] row : clientData) {
            for (Object cell : row) {
                if (cell.toString().toLowerCase().contains(searchTerm)) {
                    filteredData.add(row);
                    break;
                }
            }
        }
        
        Object[][] filteredArray = new Object[filteredData.size()][];
        filteredArray = filteredData.toArray(filteredArray);
        
        tableModel.setDataVector(filteredArray, columnNames);
        // Reapply the renderer for the actions column
        clientsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        clientsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    private void handleAddClient() {
        AddClientFrame addClientFrame = new AddClientFrame(this);
        addClientFrame.setVisible(true);
    }
    
    private void backToDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        this.dispose();
    }
    
    private void handleEditClient(int row) {
        Object[] clientData = new Object[6];
        for (int i = 0; i < 6; i++) {
            clientData[i] = tableModel.getValueAt(row, i);
        }
        EditClientFrame editFrame = new EditClientFrame(this, row, clientData);
        editFrame.setVisible(true);
    }

    public void updateClient(int row, Object[] updatedClient) {
        for (int i = 0; i < updatedClient.length; i++) {
            tableModel.setValueAt(updatedClient[i], row, i);
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
                    handleEditClient(currentRow);
                } else if (action.equals("delete")) {
                    handleDeleteClient(currentRow);
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
    
    private void handleDeleteClient(int row) {
        String clientId = (String) tableModel.getValueAt(row, 0);
        String clientName = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);
        
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete client " + clientName + " (ID: " + clientId + ")?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (response == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Client " + clientName + " deleted successfully");
        }
    }
    
    public void addClient(Object[] newClient) {
        tableModel.addRow(newClient);
        // Update the total clients count
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
                        infoLabel.setText("Total clients: " + tableModel.getRowCount());
                        break;
                    }
                }
            }
        }
    }
} 
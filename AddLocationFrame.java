import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLocationFrame extends JFrame {
    private JTextField idField;
    private JTextField clientIdField;
    private JTextField carIdField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField priceField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private LocationsListFrame parentFrame;

    public AddLocationFrame(LocationsListFrame parent) {
        this.parentFrame = parent;
        setTitle("Add New Rental");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 245));

        // ID field
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        idField.setText(generateLocationId());
        formPanel.add(idField);

        // Client ID field
        formPanel.add(new JLabel("Client ID:"));
        clientIdField = new JTextField();
        formPanel.add(clientIdField);

        // Car ID field
        formPanel.add(new JLabel("Car ID:"));
        carIdField = new JTextField();
        formPanel.add(carIdField);

        // Start Date field
        formPanel.add(new JLabel("Start Date:"));
        startDateField = new JTextField();
        startDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        formPanel.add(startDateField);

        // End Date field
        formPanel.add(new JLabel("End Date:"));
        endDateField = new JTextField();
        endDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        formPanel.add(endDateField);

        // Price field
        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        // Status field
        formPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Active", "Completed", "Cancelled"});
        formPanel.add(statusComboBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 245));

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(e -> handleSave());

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String generateLocationId() {
        // This would be replaced with actual ID generation logic
        return "L" + String.format("%03d", (int)(Math.random() * 1000));
    }

    private void handleSave() {
        // Validate fields
        if (clientIdField.getText().trim().isEmpty() ||
            carIdField.getText().trim().isEmpty() ||
            startDateField.getText().trim().isEmpty() ||
            endDateField.getText().trim().isEmpty() ||
            priceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new rental data
        Object[] newLocation = {
            idField.getText(),
            clientIdField.getText(),
            carIdField.getText(),
            startDateField.getText(),
            endDateField.getText(),
            priceField.getText(),
            statusComboBox.getSelectedItem()
        };

        // Add to parent frame's table
        parentFrame.addLocation(newLocation);
        dispose();
    }
} 
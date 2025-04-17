import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddApartmentFrame extends JFrame {
    private JTextField idField;
    private JTextField typeField;
    private JTextField cityField;
    private JTextField sizeField;
    private JTextField rentField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private ApartmentsListFrame parentFrame;

    public AddApartmentFrame(ApartmentsListFrame parent) {
        this.parentFrame = parent;
        setTitle("Add New Apartment");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 245));

        // ID field
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        idField.setText(generateApartmentId());
        formPanel.add(idField);

        // Type field
        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        // City field
        formPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        formPanel.add(cityField);

        // Size field
        formPanel.add(new JLabel("Size (m²):"));
        sizeField = new JTextField();
        formPanel.add(sizeField);

        // Rent field
        formPanel.add(new JLabel("Rent:"));
        rentField = new JTextField();
        formPanel.add(rentField);

        // Status field
        formPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Available", "Rented"});
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

    private String generateApartmentId() {
        // This would be replaced with actual ID generation logic
        return "A" + String.format("%03d", (int)(Math.random() * 1000));
    }

    private void handleSave() {
        // Validate fields
        if (typeField.getText().trim().isEmpty() ||
            cityField.getText().trim().isEmpty() ||
            sizeField.getText().trim().isEmpty() ||
            rentField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new apartment data
        Object[] newApartment = {
            idField.getText(),
            typeField.getText(),
            cityField.getText(),
            sizeField.getText(),
            rentField.getText(),
            statusComboBox.getSelectedItem()
        };

        // Add to parent frame's table
        parentFrame.addApartment(newApartment);
        dispose();
    }
} 
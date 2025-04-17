import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditApartmentFrame extends JFrame {
    private JTextField idField;
    private JTextField typeField;
    private JTextField cityField;
    private JTextField sizeField;
    private JTextField rentField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private ApartmentsListFrame parentFrame;
    private int editingRow;

    public EditApartmentFrame(ApartmentsListFrame parent, int row, Object[] apartmentData) {
        this.parentFrame = parent;
        this.editingRow = row;
        setTitle("Edit Apartment");
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
        idField.setText((String) apartmentData[0]);
        formPanel.add(idField);

        // Type field
        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField((String) apartmentData[1]);
        formPanel.add(typeField);

        // City field
        formPanel.add(new JLabel("City:"));
        cityField = new JTextField((String) apartmentData[2]);
        formPanel.add(cityField);

        // Size field
        formPanel.add(new JLabel("Size (m²):"));
        sizeField = new JTextField((String) apartmentData[3]);
        formPanel.add(sizeField);

        // Rent field
        formPanel.add(new JLabel("Rent:"));
        rentField = new JTextField((String) apartmentData[4]);
        formPanel.add(rentField);

        // Status field
        formPanel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Available", "Rented"});
        statusComboBox.setSelectedItem(apartmentData[5]);
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

        // Create updated apartment data
        Object[] updatedApartment = {
            idField.getText(),
            typeField.getText(),
            cityField.getText(),
            sizeField.getText(),
            rentField.getText(),
            statusComboBox.getSelectedItem()
        };

        // Update the parent frame's table
        parentFrame.updateApartment(editingRow, updatedApartment);
        dispose();
    }
} 
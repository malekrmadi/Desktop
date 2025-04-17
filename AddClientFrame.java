import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddClientFrame extends JFrame {
    private JTextField idField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField cityField;
    private JButton saveButton;
    private JButton cancelButton;
    private ClientsListFrame parentFrame;

    public AddClientFrame(ClientsListFrame parent) {
        this.parentFrame = parent;
        setTitle("Add New Client");
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
        idField.setText(generateClientId());
        formPanel.add(idField);

        // Last Name field
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        // First Name field
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        // Phone field
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        // Email field
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // City field
        formPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        formPanel.add(cityField);

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

    private String generateClientId() {
        // This would be replaced with actual ID generation logic
        return "C" + String.format("%03d", (int)(Math.random() * 1000));
    }

    private void handleSave() {
        // Validate fields
        if (lastNameField.getText().trim().isEmpty() ||
            firstNameField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            cityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new client data
        Object[] newClient = {
            idField.getText(),
            lastNameField.getText(),
            firstNameField.getText(),
            phoneField.getText(),
            emailField.getText(),
            cityField.getText()
        };

        // Add to parent frame's table
        parentFrame.addClient(newClient);
        dispose();
    }
} 
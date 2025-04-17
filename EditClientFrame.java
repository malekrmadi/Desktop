import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditClientFrame extends JFrame {
    private JTextField idField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField cityField;
    private JButton saveButton;
    private JButton cancelButton;
    private ClientsListFrame parentFrame;
    private int editingRow;

    public EditClientFrame(ClientsListFrame parent, int row, Object[] clientData) {
        this.parentFrame = parent;
        this.editingRow = row;
        setTitle("Edit Client");
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
        idField.setText((String) clientData[0]);
        formPanel.add(idField);

        // Last Name field
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField((String) clientData[1]);
        formPanel.add(lastNameField);

        // First Name field
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField((String) clientData[2]);
        formPanel.add(firstNameField);

        // Phone field
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField((String) clientData[3]);
        formPanel.add(phoneField);

        // Email field
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField((String) clientData[4]);
        formPanel.add(emailField);

        // City field
        formPanel.add(new JLabel("City:"));
        cityField = new JTextField((String) clientData[5]);
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

        // Create updated client data
        Object[] updatedClient = {
            idField.getText(),
            lastNameField.getText(),
            firstNameField.getText(),
            phoneField.getText(),
            emailField.getText(),
            cityField.getText()
        };

        // Update the parent frame's table
        parentFrame.updateClient(editingRow, updatedClient);
        dispose();
    }
} 
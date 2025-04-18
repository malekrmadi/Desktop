import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddAdminFrame extends JFrame {
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox superAdminCheckbox;
    private JButton saveButton;
    private JButton cancelButton;
    private AdminsListFrame parentFrame;

    public AddAdminFrame(AdminsListFrame parent) {
        this.parentFrame = parent;
        setTitle("Add New Administrator");
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
        idField.setText(generateAdminId());
        formPanel.add(idField);

        // First Name field
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        // Last Name field
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        // Email field
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // Password field
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Super Admin checkbox
        formPanel.add(new JLabel("Super Admin:"));
        superAdminCheckbox = new JCheckBox();
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkboxPanel.setBackground(new Color(240, 240, 245));
        checkboxPanel.add(superAdminCheckbox);
        formPanel.add(checkboxPanel);

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

    private String generateAdminId() {
        // This would be replaced with actual ID generation logic
        return "A" + String.format("%03d", (int)(Math.random() * 1000));
    }

    private void handleSave() {
        // Validate fields
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!isValidEmail(emailField.getText())) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new admin data
        Object[] newAdmin = {
            idField.getText(),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            "********", // Masked password for display
            superAdminCheckbox.isSelected()
        };

        // Add to parent frame's table
        parentFrame.addAdmin(newAdmin);
        dispose();
    }
    
    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
} 
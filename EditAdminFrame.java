import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditAdminFrame extends JFrame {
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox superAdminCheckbox;
    private JButton saveButton;
    private JButton cancelButton;
    private AdminsListFrame parentFrame;
    private int editingRow;

    public EditAdminFrame(AdminsListFrame parent, int row, Object[] adminData) {
        this.parentFrame = parent;
        this.editingRow = row;
        setTitle("Edit Administrator");
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
        idField.setText((String) adminData[0]);
        formPanel.add(idField);

        // First Name field
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField((String) adminData[1]);
        formPanel.add(firstNameField);

        // Last Name field
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField((String) adminData[2]);
        formPanel.add(lastNameField);

        // Email field
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField((String) adminData[3]);
        formPanel.add(emailField);

        // Password field
        formPanel.add(new JLabel("Password (leave empty to keep current):"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Super Admin checkbox
        formPanel.add(new JLabel("Super Admin:"));
        superAdminCheckbox = new JCheckBox();
        superAdminCheckbox.setSelected((Boolean) adminData[5]);
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

    private void handleSave() {
        // Validate fields
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields",
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

        // Create updated admin data
        Object[] updatedAdmin = {
            idField.getText(),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            passwordField.getPassword().length > 0 ? "********" : "********", // Keep masked password for display
            superAdminCheckbox.isSelected()
        };

        // Update the parent frame's table
        parentFrame.updateAdmin(editingRow, updatedAdmin);
        dispose();
    }
    
    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
} 
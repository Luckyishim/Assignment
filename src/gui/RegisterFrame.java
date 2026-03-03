package gui;

import models.Customer;
import utils.FileHandler;
import utils.UserFileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class RegisterFrame extends JFrame {

    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Register - Customer");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("  Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("  Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("  Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("  Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("  Address:"));
        addressField = new JTextField();
        add(addressField);

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");
        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        // basic validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters.");
            return;
        }

        if (UserFileHandler.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered.");
            return;
        }

        // generate a simple unique id
        String userId = FileHandler.generateId("data/users.txt", "CUS");

        Customer newCustomer = new Customer(userId, name, email, password, phone, address);
        UserFileHandler.saveUser(newCustomer);

        JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
        dispose();
        new LoginFrame();
    }
}
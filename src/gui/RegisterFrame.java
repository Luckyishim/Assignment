package gui;

import models.Customer;
import utils.FileHandler;
import utils.UserFileHandler;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.UUID;

public class RegisterFrame extends JFrame {

    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Register - Customer");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font labelFont  = new Font("SansSerif", Font.BOLD, 13);
        Font fieldFont  = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );

        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        setContentPane(outer);

        // Helper to add a label + field pair
        String[] labels = {"Name:", "Email:", "Password:", "Phone:", "Address:"};
        nameField     = new JTextField();
        emailField    = new JTextField();
        passwordField = new JPasswordField();
        phoneField    = new JTextField();
        addressField  = new JTextField();

        JTextField[] fields = {nameField, emailField, passwordField, phoneField, addressField};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(labelFont);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            outer.add(lbl);
            outer.add(Box.createVerticalStrut(4));

            fields[i].setFont(fieldFont);
            fields[i].setBorder(fieldBorder);
            fields[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            fields[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            outer.add(fields[i]);
            outer.add(Box.createVerticalStrut(12));
        }

        outer.add(Box.createVerticalStrut(4));

        // Button row
        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        JButton registerBtn = new JButton("Register");
        JButton backBtn     = new JButton("Back to Login");

        for (JButton btn : new JButton[]{registerBtn, backBtn}) {
            btn.setFont(buttonFont);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            buttonRow.add(btn);
        }

        outer.add(buttonRow);

        registerBtn.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> { dispose(); new LoginFrame(); });

        setVisible(true);
    }

    private void handleRegister() {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String phone    = phoneField.getText().trim();
        String address  = addressField.getText().trim();

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

        String userId = FileHandler.generateId("data/users.txt", "CUS");
        Customer newCustomer = new Customer(userId, name, email, password, phone, address);
        UserFileHandler.saveUser(newCustomer);

        JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
        dispose();
        new LoginFrame();
    }
}
package gui.customer;

import models.Customer;
import utils.UserFileHandler;

import javax.swing.*;
import java.awt.*;

public class UpdateProfileFrame extends JFrame {

    private Customer customer;
    private JTextField nameField, phoneField, addressField;

    public UpdateProfileFrame(Customer customer) {
        this.customer = customer;

        setTitle("Update Profile");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("  Name:"));
        nameField = new JTextField(customer.getName());
        add(nameField);

        add(new JLabel("  Phone:"));
        phoneField = new JTextField(customer.getPhone());
        add(phoneField);

        add(new JLabel("  Address:"));
        addressField = new JTextField(customer.getAddress());
        add(addressField);

        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");
        add(saveBtn);
        add(backBtn);

        saveBtn.addActionListener(e -> handleSave());
        backBtn.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customer);
        });

        setVisible(true);
    }

    private void handleSave() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);

        UserFileHandler.updateUser(customer);
        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        dispose();
        new CustomerDashboard(customer);
    }
}
package gui;

import models.*;
import models.InvalidLoginException;
import utils.UserFileHandler;
import gui.customer.CustomerDashboard;
import gui.scheduler.SchedulerDashboard;
import gui.admin.AdminDashboard;
import gui.manager.ManagerDashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Hall Booking System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font labelFont = new Font("SansSerif", Font.BOLD, 13);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );

        // Outer panel with padding
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        setContentPane(outer);

        // Form rows panel
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 4, 6, 4);
        gc.fill = GridBagConstraints.HORIZONTAL;

        // Email label
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        form.add(emailLabel, gc);

        // Email field
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 1.0;
        emailField = new JTextField();
        emailField.setFont(fieldFont);
        emailField.setBorder(fieldBorder);
        emailField.setPreferredSize(new Dimension(0, 30));
        form.add(emailField, gc);

        // Password label
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        form.add(passwordLabel, gc);

        // Password field
        gc.gridx = 1;
        gc.gridy = 1;
        gc.weightx = 1.0;
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBorder(fieldBorder);
        passwordField.setPreferredSize(new Dimension(0, 30));
        form.add(passwordField, gc);

        outer.add(form, BorderLayout.CENTER);

        // Button row
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(buttonFont);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton registerBtn = new JButton("Register (Customer)");
        registerBtn.setFont(buttonFont);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttons.add(loginBtn);
        buttons.add(registerBtn);
        outer.add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        getRootPane().setDefaultButton(loginBtn);
        setVisible(true);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        User user = null;
        try {
            user = UserFileHandler.loginUser(email, password);
        } catch (InvalidLoginException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        if (user instanceof Customer) {
            Customer c = (Customer) user;
            if (c.isBlocked()) {
                JOptionPane.showMessageDialog(this, "Your account has been blocked. Contact admin.");
                return;
            }
        }

        dispose();
        switch (user.getRole()) {
            case "CUSTOMER":
                new CustomerDashboard((Customer) user);
                break;
            case "SCHEDULER":
                new SchedulerDashboard((Scheduler) user);
                break;
            case "ADMIN":
                new AdminDashboard((Administrator) user);
                break;
            case "MANAGER":
                new ManagerDashboard((Manager) user);
                break;
        }
    }
}
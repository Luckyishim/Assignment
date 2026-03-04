package gui;

import models.*;
import models.InvalidLoginException;
import utils.UserFileHandler;
import gui.customer.CustomerDashboard;
import gui.scheduler.SchedulerDashboard;
import gui.admin.AdminDashboard;
import gui.manager.ManagerDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField emailField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Hall Booking System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("  Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("  Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register (Customer)");
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

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


        // check if customer is blocked
        if (user instanceof Customer) {
            Customer c = (Customer) user;
            if (c.isBlocked()) {
                JOptionPane.showMessageDialog(this, "Your account has been blocked. Contact admin.");
                return;
            }
        }

        // route to correct dashboard based on role
        dispose();
        switch (UserRole.valueOf(user.getRole())) {
            case CUSTOMER -> new CustomerDashboard((Customer) user);
            case SCHEDULER   -> new SchedulerDashboard((Scheduler) user);
            case ADMIN -> new AdminDashboard((Administrator) user);
            case MANAGER -> new ManagerDashboard((Manager) user);
        }
    }
}
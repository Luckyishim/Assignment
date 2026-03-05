package gui.customer;

import gui.LoginFrame;
import models.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    private Customer customer;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        setTitle("Customer Dashboard - Welcome " + customer.getName());
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton bookHallBtn = new JButton("Book a Hall");
        JButton viewBookingsBtn = new JButton("View My Bookings");
        JButton raiseIssueBtn = new JButton("Raise an Issue");
        JButton updateProfileBtn = new JButton("Update Profile");
        JButton logoutBtn = new JButton("Logout");

        add(new JLabel("  Hello, " + customer.getName() + "!", SwingConstants.CENTER));
        add(bookHallBtn);
        add(viewBookingsBtn);
        add(raiseIssueBtn);
        add(updateProfileBtn);
        add(logoutBtn);

        bookHallBtn.addActionListener(e -> {
            dispose();
            new BookingFrame(customer);
        });

        viewBookingsBtn.addActionListener(e -> {
            dispose();
            new ViewBookingsFrame(customer);
        });

        raiseIssueBtn.addActionListener(e -> {
            dispose();
            new IssueFrame(customer);
        });

        updateProfileBtn.addActionListener(e -> {
            dispose();
            new UpdateProfileFrame(customer);
        });

        logoutBtn.addActionListener(e -> {
            customer.logout();
            JOptionPane.showMessageDialog(this, customer.getName() + " has logged out successfully.");
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
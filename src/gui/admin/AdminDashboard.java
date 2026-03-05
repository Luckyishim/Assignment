package gui.admin;

import gui.LoginFrame;
import models.Administrator;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private Administrator admin;

    public AdminDashboard(Administrator admin) {
        this.admin = admin;

        setTitle("Admin Dashboard - " + admin.getName());
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel("  Welcome, " + admin.getName(), SwingConstants.CENTER));

        JButton schedulerMgmtBtn = new JButton("Scheduler Staff Management");
        JButton userMgmtBtn = new JButton("User Management");
        JButton bookingMgmtBtn = new JButton("View All Bookings");
        JButton logoutBtn = new JButton("Logout");

        add(schedulerMgmtBtn);
        add(userMgmtBtn);
        add(bookingMgmtBtn);
        add(logoutBtn);

        schedulerMgmtBtn.addActionListener(e -> {
            dispose();
            new SchedulerManagementFrame(admin);
        });

        userMgmtBtn.addActionListener(e -> {
            dispose();
            new UserManagementFrame(admin);
        });

        bookingMgmtBtn.addActionListener(e -> {
            dispose();
            new AdminBookingFrame(admin);
        });

        logoutBtn.addActionListener(e -> {
            admin.logout();
            JOptionPane.showMessageDialog(this, admin.getName() + " has logged out successfully.");
            dispose();
            new LoginFrame();
        });


        setVisible(true);
    }
}
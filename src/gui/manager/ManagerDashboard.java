package gui.manager;

import gui.LoginFrame;
import models.Manager;

import javax.swing.*;
import java.awt.*;

public class ManagerDashboard extends JFrame {

    private Manager manager;

    public ManagerDashboard(Manager manager) {
        this.manager = manager;

        setTitle("Manager Dashboard - " + manager.getName());
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel("  Welcome, " + manager.getName(), SwingConstants.CENTER));

        JButton salesBtn = new JButton("Sales Dashboard");
        JButton issuesBtn = new JButton("Maintenance / Issues");
        JButton logoutBtn = new JButton("Logout");

        add(salesBtn);
        add(issuesBtn);
        add(new JLabel(""));
        add(logoutBtn);

        salesBtn.addActionListener(e -> {
            dispose();
            new SalesDashboardFrame(manager);
        });

        issuesBtn.addActionListener(e -> {
            dispose();
            new IssueManagementFrame(manager);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
package gui.scheduler;

import gui.LoginFrame;
import models.Scheduler;

import javax.swing.*;
import java.awt.*;

public class SchedulerDashboard extends JFrame {

    private Scheduler scheduler;

    public SchedulerDashboard(Scheduler scheduler) {
        this.scheduler = scheduler;

        setTitle("Scheduler Dashboard - " + scheduler.getName());
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel("  Welcome, " + scheduler.getName(), SwingConstants.CENTER));

        JButton hallMgmtBtn = new JButton("Hall Management");
        JButton scheduleBtn = new JButton("Set Hall Schedule");
        JButton logoutBtn = new JButton("Logout");

        add(hallMgmtBtn);
        add(scheduleBtn);
        add(new JLabel(""));
        add(logoutBtn);

        hallMgmtBtn.addActionListener(e -> {
            dispose();
            new HallManagementFrame(scheduler);
        });

        scheduleBtn.addActionListener(e -> {
            dispose();
            new HallScheduleFrame(scheduler);
        });

        logoutBtn.addActionListener(e -> {
            scheduler.logout();
            JOptionPane.showMessageDialog(this, scheduler.getName() + " has logged out successfully.");
            dispose();
            new LoginFrame();
        });


        setVisible(true);
    }
}
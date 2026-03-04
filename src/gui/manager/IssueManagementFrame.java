package gui.manager;

import models.*;
import utils.BookingFileHandler;
import utils.UserFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IssueManagementFrame extends JFrame {

    private Manager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Issue> issues;

    public IssueManagementFrame(Manager manager) {
        this.manager = manager;

        setTitle("Issue Management");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // table
        String[] cols = {"Issue ID", "Customer ID", "Booking ID", "Description", "Response", "Assigned To", "Status"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton respondBtn = new JButton("Respond to Issue");
        JButton assignBtn = new JButton("Assign Scheduler");
        JButton statusBtn = new JButton("Change Status");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(respondBtn);
        bottomPanel.add(assignBtn);
        bottomPanel.add(statusBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadIssues();

        respondBtn.addActionListener(e -> handleRespond());
        assignBtn.addActionListener(e -> handleAssign());
        statusBtn.addActionListener(e -> handleStatusChange());
        backBtn.addActionListener(e -> {
            dispose();
            new ManagerDashboard(manager);
        });

        setVisible(true);
    }

    private void loadIssues() {
        tableModel.setRowCount(0);
        issues = BookingFileHandler.getAllIssues();
        for (Issue i : issues) {
            tableModel.addRow(new Object[]{
                    i.getIssueId(), i.getCustomerId(), i.getBookingId(),
                    i.getDescription(), i.getManagerResponse(),
                    i.getAssignedSchedulerId(), i.getStatus()
            });
        }
    }

    private void handleRespond() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an issue.");
            return;
        }

        Issue selected = issues.get(selectedRow);
        String response = JOptionPane.showInputDialog(this, "Enter your response:", selected.getManagerResponse());
        if (response != null && !response.trim().isEmpty()) {
            selected.setManagerResponse(response.trim());
            BookingFileHandler.updateIssue(selected);
            JOptionPane.showMessageDialog(this, "Response saved.");
            loadIssues();
        }
    }

    private void handleAssign() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an issue.");
            return;
        }

        Issue selected = issues.get(selectedRow);
        List<Scheduler> schedulers = UserFileHandler.getAllSchedulers();

        if (schedulers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No schedulers available.");
            return;
        }

        String[] options = new String[schedulers.size()];
        for (int i = 0; i < schedulers.size(); i++) {
            options[i] = schedulers.get(i).getUserId() + " - " + schedulers.get(i).getName();
        }

        String choice = (String) JOptionPane.showInputDialog(this,
                "Select Scheduler to assign:", "Assign Scheduler",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice != null) {
            String schedulerId = choice.split(" - ")[0];
            selected.setAssignedSchedulerId(schedulerId);
            BookingFileHandler.updateIssue(selected);
            JOptionPane.showMessageDialog(this, "Scheduler assigned.");
            loadIssues();
        }
    }

    private void handleStatusChange() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an issue.");
            return;
        }

        Issue selected = issues.get(selectedRow);
        String[] statusOptions = {
                IssueStatus.IN_PROGRESS.name(),
                IssueStatus.DONE.name(),
                IssueStatus.CLOSED.name(),
                IssueStatus.CANCELLED.name()
        };

        String choice = (String) JOptionPane.showInputDialog(this,
                "Select new status:", "Change Status",
                JOptionPane.PLAIN_MESSAGE, null, statusOptions, selected.getStatus());

        if (choice != null) {
            selected.setStatus(choice);
            BookingFileHandler.updateIssue(selected);
            JOptionPane.showMessageDialog(this, "Status updated to: " + choice);
            loadIssues();
        }
    }
}
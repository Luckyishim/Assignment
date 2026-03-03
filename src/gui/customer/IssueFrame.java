package gui.customer;

import models.*;
import utils.BookingFileHandler;
import utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IssueFrame extends JFrame {

    private Customer customer;
    private JComboBox<String> bookingBox;
    private JTextArea descArea;
    private List<Booking> confirmedBookings;

    public IssueFrame(Customer customer) {
        this.customer = customer;

        setTitle("Raise an Issue");
        setSize(450, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.add(new JLabel("  Select Booking:"));
        bookingBox = new JComboBox<>();
        loadConfirmedBookings();
        topPanel.add(bookingBox);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JLabel("  Describe the Issue:"), BorderLayout.NORTH);
        descArea = new JTextArea(6, 30);
        descArea.setLineWrap(true);
        centerPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton submitBtn = new JButton("Submit Issue");
        JButton viewBtn = new JButton("View My Issues");
        JButton backBtn = new JButton("Back");
        bottomPanel.add(submitBtn);
        bottomPanel.add(viewBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> handleSubmit());
        viewBtn.addActionListener(e -> viewMyIssues());
        backBtn.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customer);
        });

        setVisible(true);
    }

    private void loadConfirmedBookings() {
        confirmedBookings = BookingFileHandler.getBookingsByCustomer(customer.getUserId());
        for (Booking b : confirmedBookings) {
            if (b.getStatus().equals("CONFIRMED")) {
                bookingBox.addItem(b.getBookingId() + " | " + b.getBookingDate());
            }
        }
    }

    private void handleSubmit() {
        if (bookingBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No confirmed bookings to raise issue for.");
            return;
        }

        String description = descArea.getText().trim();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please describe the issue.");
            return;
        }

        int selectedIndex = bookingBox.getSelectedIndex();
        Booking selected = confirmedBookings.get(selectedIndex);

        String issueId = FileHandler.generateId("data/issues.txt", "ISS");
        Issue issue = new Issue(issueId, customer.getUserId(), selected.getBookingId(), description);
        BookingFileHandler.saveIssue(issue);

        JOptionPane.showMessageDialog(this, "Issue submitted successfully!");
        descArea.setText("");
    }

    private void viewMyIssues() {
        List<Issue> issues = BookingFileHandler.getIssuesByCustomer(customer.getUserId());
        if (issues.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no issues raised.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Issue i : issues) {
            sb.append("Issue ID: ").append(i.getIssueId()).append("\n");
            sb.append("Booking : ").append(i.getBookingId()).append("\n");
            sb.append("Status  : ").append(i.getStatus()).append("\n");
            sb.append("Response: ").append(i.getManagerResponse()).append("\n");
            sb.append("Assigned: ").append(i.getAssignedSchedulerId()).append("\n");
            sb.append("------------------------------\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "My Issues", JOptionPane.PLAIN_MESSAGE);
    }
}
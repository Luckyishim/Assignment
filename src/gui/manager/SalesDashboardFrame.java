package gui.manager;

import models.Manager;
import models.Payment;
import utils.BookingFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesDashboardFrame extends JFrame {

    private Manager manager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public SalesDashboardFrame(Manager manager) {
        this.manager = manager;

        setTitle("Sales Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // filter panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filter:"));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{
                "ALL", "THIS WEEK", "THIS MONTH", "THIS YEAR"
        });
        JButton filterBtn = new JButton("Apply");
        topPanel.add(filterBox);
        topPanel.add(filterBtn);
        add(topPanel, BorderLayout.NORTH);

        // table
        String[] cols = {"Payment ID", "Booking ID", "Amount (RM)", "Date", "Method"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // total + back
        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("  Total Revenue: RM 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new ManagerDashboard(manager);
        });
        bottomPanel.add(backBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        loadPayments("ALL");

        filterBtn.addActionListener(e -> loadPayments((String) filterBox.getSelectedItem()));

        setVisible(true);
    }

    private void loadPayments(String filter) {
        tableModel.setRowCount(0);
        List<Payment> payments = BookingFileHandler.getAllPayments();
        double total = 0;

        java.time.LocalDate today = java.time.LocalDate.now();

        for (Payment p : payments) {
            // parse payment date "dd-MM-yyyy"
            String[] parts = p.getPaymentDate().split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            java.time.LocalDate payDate = java.time.LocalDate.of(year, month, day);

            boolean include = false;
            if (filter.equals("ALL")) {
                include = true;
            } else if (filter.equals("THIS WEEK")) {
                // within last 7 days
                include = !payDate.isBefore(today.minusDays(7));
            } else if (filter.equals("THIS MONTH")) {
                include = payDate.getMonthValue() == today.getMonthValue() &&
                        payDate.getYear() == today.getYear();
            } else if (filter.equals("THIS YEAR")) {
                include = payDate.getYear() == today.getYear();
            }

            if (include) {
                total += p.getAmount();
                tableModel.addRow(new Object[]{
                        p.getPaymentId(), p.getBookingId(), p.getAmount(), p.getPaymentDate(), p.getPaymentMethod()
                });
            }
        }

        totalLabel.setText("  Total Revenue: RM " + String.format("%.2f", total));
    }
}
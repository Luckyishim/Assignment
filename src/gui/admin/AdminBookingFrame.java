package gui.admin;

import models.Administrator;
import models.HallNotFoundException;
import models.Booking;
import utils.BookingFileHandler;
import utils.HallFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminBookingFrame extends JFrame {

    private Administrator admin;
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminBookingFrame(Administrator admin) {
        this.admin = admin;

        setTitle("All Bookings");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filter by status:"));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"ALL", "CONFIRMED", "CANCELLED", "COMPLETED"});
        JButton filterBtn = new JButton("Filter");
        topPanel.add(filterBox);
        topPanel.add(filterBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"Booking ID", "Customer ID", "Hall", "Date", "Start", "End", "Total (RM)", "Status"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton backBtn = new JButton("Back");
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBookings("ALL");

        filterBtn.addActionListener(e -> loadBookings((String) filterBox.getSelectedItem()));
        backBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard(admin);
        });

        setVisible(true);
    }

    private void loadBookings(String filter) {
        tableModel.setRowCount(0);
        List<Booking> bookings = BookingFileHandler.getAllBookings();
        for (Booking b : bookings) {
            if (!filter.equals("ALL") && !b.getStatus().equals(filter)) continue;
            String hallName;
            try {
                hallName = HallFileHandler.getHallById(b.getHallId()).getHallName();
            } catch (HallNotFoundException ex) {
                hallName = b.getHallId(); // fallback to ID if hall not found
            }
            tableModel.addRow(new Object[]{
                    b.getBookingId(), b.getCustomerId(), hallName,
                    b.getBookingDate(), b.getStartTime(), b.getEndTime(),
                    b.getTotalAmount(), b.getStatus()
            });
        }
    }
}
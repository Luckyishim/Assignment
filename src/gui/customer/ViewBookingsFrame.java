package gui.customer;

import models.*;
import models.InvalidBookingException;
import models.HallNotFoundException;
import utils.BookingFileHandler;
import utils.HallFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewBookingsFrame extends JFrame {

    private Customer customer;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Booking> bookings;

    public ViewBookingsFrame(Customer customer) {
        this.customer = customer;

        setTitle("My Bookings");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // filter panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filter by status:"));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"ALL", "CONFIRMED", "CANCELLED", "COMPLETED"});
        topPanel.add(filterBox);
        JButton filterBtn = new JButton("Filter");
        topPanel.add(filterBtn);
        add(topPanel, BorderLayout.NORTH);

        // table
        String[] columns = {"Booking ID", "Hall", "Date", "Start", "End", "Total (RM)", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton cancelBtn = new JButton("Cancel Selected Booking");
        JButton backBtn = new JButton("Back");
        bottomPanel.add(cancelBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBookings("ALL");

        filterBtn.addActionListener(e -> loadBookings((String) filterBox.getSelectedItem()));

        cancelBtn.addActionListener(e -> handleCancel());

        backBtn.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customer);
        });

        setVisible(true);
    }

    private void loadBookings(String filter) {
        tableModel.setRowCount(0);
        bookings = BookingFileHandler.getBookingsByCustomer(customer.getUserId());

        for (Booking b : bookings) {
            if (!filter.equals("ALL") && !b.getStatus().equals(filter)) continue;

            String hallName;
            try {
                Hall hall = HallFileHandler.getHallById(b.getHallId());
                hallName = hall.getHallName();
            } catch (HallNotFoundException ex) {
                hallName = b.getHallId(); // fallback to just showing the ID
            }

            tableModel.addRow(new Object[]{
                    b.getBookingId(),
                    hallName,
                    b.getBookingDate(),
                    b.getStartTime(),
                    b.getEndTime(),
                    b.getTotalAmount(),
                    b.getStatus()
            });
        }
    }

    private void handleCancel() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");
            return;
        }

        Booking selected = bookings.get(selectedRow);

        if (!selected.getStatus().equals("CONFIRMED")) {
            JOptionPane.showMessageDialog(this, "Only confirmed bookings can be cancelled.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?",
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                selected.cancel(); // throws InvalidBookingException if less than 3 days away
                BookingFileHandler.updateBooking(selected);
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                loadBookings("ALL");
            } catch (InvalidBookingException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
}
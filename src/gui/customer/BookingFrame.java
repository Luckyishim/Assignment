package gui.customer;

import models.*;
import utils.BookingFileHandler;
import utils.FileHandler;
import utils.HallFileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingFrame extends JFrame {

    private Customer customer;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<HallSchedule> schedules;

    public BookingFrame(Customer customer) {
        this.customer = customer;

        setTitle("Book a Hall");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // table to show available halls
        String[] columns = {"Schedule ID", "Hall ID", "Hall Name", "Type", "Capacity", "Rate/hr", "From", "To", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadAvailableHalls();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton bookBtn = new JButton("Book Selected");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(bookBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        bookBtn.addActionListener(e -> handleBook());
        backBtn.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customer);
        });

        setVisible(true);
    }

    private void loadAvailableHalls() {
        tableModel.setRowCount(0);
        schedules = HallFileHandler.getAllAvailableSchedules();
        for (HallSchedule s : schedules) {
            Hall hall = HallFileHandler.getHallById(s.getHallId());
            if (hall != null) {
                tableModel.addRow(new Object[]{
                        s.getScheduleId(),
                        hall.getHallId(),
                        hall.getHallName(),
                        hall.getHallType(),
                        hall.getCapacity(),
                        "RM " + hall.getRatePerHour(),
                        s.getStartDateTime(),
                        s.getEndDateTime(),
                        s.getRemarks()
                });
            }
        }
    }

    private void handleBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hall to book.");
            return;
        }

        HallSchedule selected = schedules.get(selectedRow);
        Hall hall = HallFileHandler.getHallById(selected.getHallId());

        // calculate hours and total
        // startDateTime and endDateTime format: "dd-MM-yyyy HH:mm"
        String start = selected.getStartDateTime();
        String end = selected.getEndDateTime();

        double hours = calculateHours(start, end);
        double total = hours * hall.getRatePerHour();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Hall: " + hall.getHallName() + "\nFrom: " + start + "\nTo: " + end +
                        "\nTotal: RM " + total + "\n\nProceed to payment?",
                "Confirm Booking", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String bookingId = FileHandler.generateId("data/bookings.txt", "BOK");
            // use booking date as the start date part
            String bookingDate = start.split(" ")[0];
            String startTime = start.split(" ")[1];
            String endTime = end.split(" ")[1];

            Booking booking = new Booking(bookingId, customer.getUserId(), hall.getHallId(),
                    bookingDate, startTime, endTime, total);
            BookingFileHandler.saveBooking(booking);

            dispose();
            new PaymentFrame(customer, booking, hall);
        }
    }

    // simple hour calculation from "dd-MM-yyyy HH:mm" strings
    private double calculateHours(String start, String end) {
        try {
            int startHour = Integer.parseInt(start.split(" ")[1].split(":")[0]);
            int startMin = Integer.parseInt(start.split(" ")[1].split(":")[1]);
            int endHour = Integer.parseInt(end.split(" ")[1].split(":")[0]);
            int endMin = Integer.parseInt(end.split(" ")[1].split(":")[1]);
            return ((endHour * 60 + endMin) - (startHour * 60 + startMin)) / 60.0;
        } catch (Exception e) {
            return 1; // default to 1 hour if parsing fails
        }
    }
}
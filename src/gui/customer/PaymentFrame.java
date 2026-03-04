package gui.customer;

import models.*;
import models.InvalidBookingException;
import utils.BookingFileHandler;
import utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentFrame extends JFrame {

    private Customer customer;
    private Booking booking;
    private Hall hall;
    private JComboBox<String> methodBox;

    public PaymentFrame(Customer customer, Booking booking, Hall hall) {
        this.customer = customer;
        this.booking = booking;
        this.hall = hall;

        setTitle("Payment");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("  Hall:"));
        add(new JLabel(hall.getHallName()));

        add(new JLabel("  Date:"));
        add(new JLabel(booking.getBookingDate()));

        add(new JLabel("  Total Amount:"));
        add(new JLabel("RM " + booking.getTotalAmount()));

        add(new JLabel("  Payment Method:"));
        methodBox = new JComboBox<>(new String[]{"CASH", "CARD", "ONLINE"});
        add(methodBox);

        JButton payBtn = new JButton("Pay Now");
        JButton cancelBtn = new JButton("Cancel");
        add(payBtn);
        add(cancelBtn);

        payBtn.addActionListener(e -> handlePayment());
        cancelBtn.addActionListener(e -> {
            try {
                booking.cancel(); // throws InvalidBookingException if not cancellable
                BookingFileHandler.updateBooking(booking);
            } catch (InvalidBookingException ex) {
                // even if not cancellable just go back to dashboard
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            dispose();
            new CustomerDashboard(customer);
        });

        setVisible(true);
    }

    private void handlePayment() {
        String method = (String) methodBox.getSelectedItem();
        String paymentId = FileHandler.generateId("data/payments.txt", "PAY");
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Payment payment = new Payment(paymentId, booking.getBookingId(),
                booking.getTotalAmount(), today, method);
        BookingFileHandler.savePayment(payment);

        JOptionPane.showMessageDialog(this, "Payment successful!");
        dispose();
        new ReceiptFrame(customer, booking, hall, payment);
    }
}
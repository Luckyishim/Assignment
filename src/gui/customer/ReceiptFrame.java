package gui.customer;

import models.*;

import javax.swing.*;
import java.awt.*;

public class ReceiptFrame extends JFrame {

    public ReceiptFrame(Customer customer, Booking booking, Hall hall, Payment payment) {

        setTitle("Booking Receipt");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // build the receipt as plain text in a text area
        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        String receipt =
                "==============================\n" +
                        "       HALL SYMPHONY INC.     \n" +
                        "          BOOKING RECEIPT     \n" +
                        "==============================\n" +
                        "Receipt ID  : " + payment.getPaymentId() + "\n" +
                        "Booking ID  : " + booking.getBookingId() + "\n" +
                        "------------------------------\n" +
                        "Customer    : " + customer.getName() + "\n" +
                        "Email       : " + customer.getEmail() + "\n" +
                        "Phone       : " + customer.getPhone() + "\n" +
                        "------------------------------\n" +
                        "Hall        : " + hall.getHallName() + "\n" +
                        "Type        : " + hall.getHallType() + "\n" +
                        "Capacity    : " + hall.getCapacity() + " pax\n" +
                        "Date        : " + booking.getBookingDate() + "\n" +
                        "Time        : " + booking.getStartTime() + " - " + booking.getEndTime() + "\n" +
                        "------------------------------\n" +
                        "Rate/hour   : RM " + hall.getRatePerHour() + "\n" +
                        "Total Paid  : RM " + payment.getAmount() + "\n" +
                        "Method      : " + payment.getPaymentMethod() + "\n" +
                        "Payment Date: " + payment.getPaymentDate() + "\n" +
                        "==============================\n" +
                        "   Thank you for booking!     \n" +
                        "==============================\n";

        receiptArea.setText(receipt);

        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        JButton doneBtn = new JButton("Done");
        doneBtn.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customer);
        });

        JPanel bottom = new JPanel();
        bottom.add(doneBtn);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
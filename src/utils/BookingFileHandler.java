package utils;



import models.Booking;
import models.Issue;
import models.Payment;
import java.util.ArrayList;
import java.util.List;

public class BookingFileHandler extends FileHandler {

    private static final String BOOKINGS_FILE = "txt-data/bookings.txt";
    private static final String PAYMENTS_FILE = "txt-data/payments.txt";
    private static final String ISSUES_FILE = "txt-data/issues.txt";

    // ---- BOOKING METHODS ----

    public static void saveBooking(Booking booking) {
        appendLine(BOOKINGS_FILE, booking.toFileString());
    }

    public static void updateBooking(Booking booking) {
        updateById(BOOKINGS_FILE, booking.getBookingId(), booking.toFileString());
    }

    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        List<String> lines = readAll(BOOKINGS_FILE);
        for (String line : lines) {
            bookings.add(Booking.fromFileString(line));
        }
        return bookings;
    }

    // get bookings for a specific customer
    public static List<Booking> getBookingsByCustomer(String customerId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : getAllBookings()) {
            if (b.getCustomerId().equals(customerId)) {
                result.add(b);
            }
        }
        return result;
    }

    // get bookings by status
    public static List<Booking> getBookingsByStatus(String status) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : getAllBookings()) {
            if (b.getStatus().equals(status)) {
                result.add(b);
            }
        }
        return result;
    }

    public static Booking getBookingById(String bookingId) {
        List<String> lines = readAll(BOOKINGS_FILE);
        for (String line : lines) {
            if (line.startsWith(bookingId + "|")) {
                return Booking.fromFileString(line);
            }
        }
        return null;
    }

    // ---- PAYMENT METHODS ----

    public static void savePayment(Payment payment) {
        appendLine(PAYMENTS_FILE, payment.toFileString());
    }

    public static List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        List<String> lines = readAll(PAYMENTS_FILE);
        for (String line : lines) {
            payments.add(Payment.fromFileString(line));
        }
        return payments;
    }

    public static Payment getPaymentByBookingId(String bookingId) {
        for (Payment p : getAllPayments()) {
            if (p.getBookingId().equals(bookingId)) {
                return p;
            }
        }
        return null;
    }

    // total revenue for a given month e.g. "03-2025"
    public static double getMonthlyRevenue(String monthYear) {
        double total = 0;
        for (Payment p : getAllPayments()) {
            // paymentDate is "dd-MM-yyyy", extract "MM-yyyy"
            String[] parts = p.getPaymentDate().split("-");
            String myStr = parts[1] + "-" + parts[2];
            if (myStr.equals(monthYear)) {
                total += p.getAmount();
            }
        }
        return total;
    }

    // ---- ISSUE METHODS ----

    public static void saveIssue(Issue issue) {
        appendLine(ISSUES_FILE, issue.toFileString());
    }

    public static void updateIssue(Issue issue) {
        updateById(ISSUES_FILE, issue.getIssueId(), issue.toFileString());
    }

    public static List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        List<String> lines = readAll(ISSUES_FILE);
        for (String line : lines) {
            issues.add(Issue.fromFileString(line));
        }
        return issues;
    }

    public static List<Issue> getIssuesByCustomer(String customerId) {
        List<Issue> result = new ArrayList<>();
        for (Issue i : getAllIssues()) {
            if (i.getCustomerId().equals(customerId)) {
                result.add(i);
            }
        }
        return result;
    }

    public static Issue getIssueById(String issueId) {
        for (Issue i : getAllIssues()) {
            if (i.getIssueId().equals(issueId)) {
                return i;
            }
        }
        return null;
    }
}
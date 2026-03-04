package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Booking {

    private String bookingId;
    private String customerId;
    private String hallId;
    private String bookingDate;   // "dd-MM-yyyy"
    private String startTime;     // "HH:mm"
    private String endTime;       // "HH:mm"
    private double totalAmount;
    private String status;        // CONFIRMED, CANCELLED, COMPLETED

    public Booking(String bookingId, String customerId, String hallId,
                   String bookingDate, String startTime, String endTime, double totalAmount) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.hallId = hallId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
    }

    // check if booking can be cancelled (must be at least 3 days before booking date)
    public boolean isCancellable() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate bDate = LocalDate.parse(bookingDate, formatter);
        LocalDate today = LocalDate.now();
        long daysUntil = ChronoUnit.DAYS.between(today, bDate);
        return daysUntil >= 3;
    }

    // now throws InvalidBookingException instead of silently doing nothing
    public void cancel() throws InvalidBookingException {
        if (!isCancellable()) {
            throw new InvalidBookingException("Cannot cancel — booking must be at least 3 days away.");
        }
        this.status = "CANCELLED";
    }

    // getters
    public String getBookingId() { return bookingId; }
    public String getCustomerId() { return customerId; }
    public String getHallId() { return hallId; }
    public String getBookingDate() { return bookingDate; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    // setters
    public void setStatus(String status) { this.status = status; }

    // format: bookingId|customerId|hallId|bookingDate|startTime|endTime|totalAmount|status
    public String toFileString() {
        return bookingId + "|" + customerId + "|" + hallId + "|" + bookingDate + "|" +
                startTime + "|" + endTime + "|" + totalAmount + "|" + status;
    }

    // rebuild Booking object from a line in bookings.txt
    public static Booking fromFileString(String line) {
        String[] parts = line.split("\\|");
        Booking b = new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], Double.parseDouble(parts[6]));
        b.setStatus(parts[7]);
        return b;
    }
}
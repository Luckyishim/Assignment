package models;

public class Payment {

    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentDate; // "dd-MM-yyyy"
    private String paymentMethod; // CASH, CARD, ONLINE

    public Payment(String paymentId, String bookingId, double amount,
                   String paymentDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    // getters
    public String getPaymentId() { return paymentId; }
    public String getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }

    // format: paymentId|bookingId|amount|paymentDate|paymentMethod
    public String toFileString() {
        return paymentId + "|" + bookingId + "|" + amount + "|" + paymentDate + "|" + paymentMethod;
    }

    // rebuild Payment object from a line in payments.txt
    public static Payment fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Payment(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]);
    }
}
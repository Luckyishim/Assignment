package models;

import java.time.LocalDateTime;

public class Payment {
    private String paymentID;
    private String bookingID;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;

    //Parameterized Constructor
    public Payment(String payID, Booking booking, String method){
        this.paymentID = payID;
        this.bookingID = booking.getBookingID();
        this.amount = booking.getTotalAmount();
        this.paymentMethod = method;
        this.paymentDate = LocalDateTime.now();
    }

    //Getter for all fields

    public String getPaymentID() {
        return paymentID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    //Method for payment
    public void generateReceipts(){

    }
}

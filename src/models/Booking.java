package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private String bookingID;
    private String customerID;
    private String hallID;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double totalAmount;
    private String status;

    //Parameterized Constructor
    public Booking(String bID, Customer customer, Hall hall, LocalDateTime start, LocalDateTime end) {
        this.bookingID = bID;
        this.customerID = customer.getUserID();
        this.hallID = hall.getHallID();
        this.startDateTime = start;
        this.endDateTime = end;

        //Default Status
        this.status = "Pending";
        calculateTotal(hall.getRatePerHour());

    }
    //Getters methods for all the field
    public String getBookingID() {
        return bookingID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getHallID() {
        return hallID;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    //Setter methods for fields
    public void setStatus(String status){
        this.status = status;
    }
    //Just in case they change time
    public void setTimes(LocalDateTime start, LocalDateTime end, double rate){
        this.startDateTime = start;
        this.endDateTime = end;
        calculateTotal(rate);
    }

    //Logic behind calculating total amount
    private void calculateTotal(double rate) {
        long hours = ChronoUnit.HOURS.between(startDateTime, endDateTime);

        if (hours <= 0) hours = 1;

        this.totalAmount = hours * rate;
    }

    //Method to cancel booking (must be 3 days before the booking date)
    public boolean canCancel() {
        return LocalDateTime.now().plusDays(3).isBefore(startDateTime);
    }


}



package models;

// thrown when a booking action is not allowed
// example: trying to cancel a booking that is less than 3 days away
public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
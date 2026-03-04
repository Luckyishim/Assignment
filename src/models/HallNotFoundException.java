package models;

// thrown when a hall ID is looked up but doesn't exist in halls.txt
public class HallNotFoundException extends Exception {
    public HallNotFoundException(String message) {
        super(message);
    }
}
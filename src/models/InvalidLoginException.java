package models;

// thrown when email or password doesn't match any user in users.txt
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
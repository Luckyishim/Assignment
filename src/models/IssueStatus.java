package models;

public enum IssueStatus {
    PENDING,    //First state before customer does anything
    IN_PROGRESS, //When staffs and manager are working with the issues
    DONE,   //Issue is fixed but not closed
    CLOSED, //Issue has been solved
    CANCELLED //If the issue given was invalid or wrong.
}

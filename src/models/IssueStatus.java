package models;

public enum IssueStatus {
    IN_PROGRESS, //When staffs and manager are working with the issues
    DONE,   //Issue is fixed but not closed
    CLOSED, //Issue has been solved
    CANCELLED //If the issue given was invalid or wrong.
}

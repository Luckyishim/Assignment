package models;

public class Issue {

    private final String issueId;
    private final String customerId;
    private final String bookingId;
    private final String description;
    private String managerResponse;
    private String assignedSchedulerId;
    private String status; // IN_PROGRESS, DONE, CLOSED, CANCELLED

    public Issue(String issueId, String customerId, String bookingId, String description) {
        this.issueId = issueId;
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.description = description;
        this.managerResponse = "No response yet";
        this.assignedSchedulerId = "None";
        this.status = IssueStatus.IN_PROGRESS.name();
    }

    // getters
    public String getIssueId() {
        return issueId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getDescription() {
        return description;
    }

    public String getManagerResponse() {
        return managerResponse;
    }

    public String getAssignedSchedulerId() {
        return assignedSchedulerId;
    }

    public String getStatus() {
        return status;
    }

    // setters
    public void setManagerResponse(String managerResponse) {
        this.managerResponse = managerResponse;
    }

    public void setAssignedSchedulerId(String assignedSchedulerId) {
        this.assignedSchedulerId = assignedSchedulerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // format: issueId|customerId|bookingId|description|managerResponse|assignedSchedulerId|status
    public String toFileString() {
        return issueId + "|" + customerId + "|" + bookingId + "|" + description + "|" + managerResponse + "|" + assignedSchedulerId + "|" + status;
    }

    // rebuild Issue object from a line in issues.txt
    public static Issue fromFileString(String line) {
        String[] parts = line.split("\\|");
        Issue i = new Issue(parts[0], parts[1], parts[2], parts[3]);
        i.setManagerResponse(parts[4]);
        i.setAssignedSchedulerId(parts[5]);
        i.setStatus(parts[6]);
        return i;
    }
}
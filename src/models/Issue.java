package models;

import java.time.LocalDateTime;

public class Issue {
    private String issueID;
    private String customerID;
    private String description;
    private IssueStatus status;
    private LocalDateTime createdAt;
    private String assignedSchedulerID;


    //Parameterized Constructor
    public Issue(String issueID, Customer customer, String description) {
        this.issueID = issueID;
        this.customerID = customer.getUserID();
        this.description = description;

        //Defaults logical
        this.status = IssueStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.assignedSchedulerID = "Unassigned";
    }

    //Getter methods
    public String getIssueID() {
        return issueID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAssignedSchedulerID() {
        return assignedSchedulerID;
    }
//Setter methods

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public void setAssignedSchedulerID(String assignedSchedulerID) {
        this.assignedSchedulerID = assignedSchedulerID;
    }
}

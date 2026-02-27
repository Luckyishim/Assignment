package models;

public class Manager extends User {
    private String managerID;

    //Parameterized Constructor
    public Manager(String userID, String name, String email, String password, String phone, String managerID) {
        super(userID, name, email, password, phone);
        this.managerID = managerID;
    }
    //Getter and Setter for all the fields

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    //Methods used by the Manager
    public void viewSalesDashboard() {

    }

    public void respondToIssue() {

    }

    public void assignScheduler() {

    }

//    public void updateIssueStatus(Issue issue, IssueStatus newStatus) {
//    issue.setStatus(newStatus);
//
//    }
}

package models;

public class Scheduler extends User {
    private String staffID;

    //Parameterized Constructor
    public Scheduler(String userID, String name, String email, String password, String phone, String staffID) {
        super(userID, name, email, password, phone);
        this.staffID = staffID;
    }

    //Getter and Setter for all the fields
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    //Methods used by the Scheduler/Staff
    public void addHall() {

    }

    public void viewHalls() {

    }

    public void editHall() {

    }

    public void deleteHall() {

    }

    public void setHallSchedule() {

    }
    public void setMaintenance(){

    }
}

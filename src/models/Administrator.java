package models;

public class Administrator extends User {
    private String adminID;

    //Parameterized Constructor
    public Administrator(String userID, String name, String email, String password, String phone, String adminID) {
        super(userID, name, email, password, phone);
        this.adminID = adminID;
    }

    //Getter and Setter for all the fields

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    //Methods used by the Administrator
    public void manageSchedulers() {

    }

    public void manageUsers() {

    }

    public void blockUser() {

    }

    public void viewAllBookings() {

    }
}

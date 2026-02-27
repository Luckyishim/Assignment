package models;

public class Customer extends User {
    private String address;

    //Parameterized Constructor
    public Customer(String userID, String name, String email, String password, String phone,  boolean isBlocked, UserRole role, String address) {
        super(userID, name, email, password, phone, isBlocked, role);
        this.address = address;
    }

    //Getter and Setter for all the fields

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //Methods used by the Customer
    public void register() {

    }

    public void updateProfile() {

    }

    public void makeBooking() {

    }

    public void cancelBooking() {

    }

    public void viewBooking() {

    }

    public void raiseIssue() {

    }
    public boolean canCancel(Booking booking){
        return true;
    }

    @Override
    public String toFileFormat(){
        return getUserID()+ "|" +
                getName() + "|" +
                getEmail() + "|" +
                getPassword() + "|" +
                getPhone() + "|" +
                isBlocked() + "|" +
                getRole() + "|" +
                address;
    }

}

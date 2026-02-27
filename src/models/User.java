package models;

public abstract class User {
    private String userID;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean isBlocked;

    //Parameterized Constructor
    public User(String userID, String name, String email, String password, String phone) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isBlocked = false;
    }

    //Getter and Setter for all the fields

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    // login and logout methods
  public boolean login(String inputId, String inputPass){
        return this.userID.equals(inputId) && this.password.equals(inputPass);
    }

    public void logout(){
        System.out.println(userID + "has logged off.");
    }
}

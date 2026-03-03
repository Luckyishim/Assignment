package models;

public abstract class User {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role; // CUSTOMER, SCHEDULER, ADMIN, MANAGER

    public User(String userId, String name, String email, String password, String phone, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    // every user must implement their own login check
    public abstract boolean login(String email, String password);

    public void logout() {
        System.out.println(name + " has logged out.");
    }

    // getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }

    // setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }

    // convert to a single line string to save into txt file
    // format: userId|name|email|password|phone|role
    public String toFileString() {
        return userId + "|" + name + "|" + email + "|" + password + "|" + phone + "|" + role;
    }
}
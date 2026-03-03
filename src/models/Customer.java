package models;

public class Customer extends User {

    private String address;
    private boolean isBlocked;

    public Customer(String userId, String name, String email, String password, String phone, String address) {
        super(userId, name, email, password, phone, "CUSTOMER");
        this.address = address;
        this.isBlocked = false;
    }

    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }

    // getters
    public String getAddress() { return address; }
    public boolean isBlocked() { return isBlocked; }

    // setters
    public void setAddress(String address) { this.address = address; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    // format: userId|name|email|password|phone|role|address|isBlocked
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + address + "|" + isBlocked;
    }

    // rebuild Customer object from a line in users.txt
    public static Customer fromFileString(String line) {
        String[] parts = line.split("\\|");
        Customer c = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4], parts[6]);
        c.setBlocked(Boolean.parseBoolean(parts[7]));
        return c;
    }
}
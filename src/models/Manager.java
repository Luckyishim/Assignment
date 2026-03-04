package models;

public class Manager extends User {

    private final String managerId;

    public Manager(String userId, String managerId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone, "MANAGER");
        this.managerId = managerId;
    }

    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }

    // getters
    public String getManagerId() {
        return managerId;
    }

    // format: userId|name|email|password|phone|role|managerId
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + managerId;
    }

    // rebuild Manager object from a line in users.txt
    public static Manager fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Manager(parts[0], parts[6], parts[1], parts[2], parts[3], parts[4]);
    }
}
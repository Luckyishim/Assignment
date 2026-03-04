package models;

public class Administrator extends User {

    private final String adminId;

    public Administrator(String userId, String adminId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone, "ADMIN");
        this.adminId = adminId;
    }

    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }

    // getters
    public String getAdminId() {
        return adminId;
    }

    // format: userId|name|email|password|phone|role|adminId
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + adminId;
    }

    // rebuild Administrator object from a line in users.txt
    public static Administrator fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Administrator(parts[0], parts[6], parts[1], parts[2], parts[3], parts[4]);
    }
}
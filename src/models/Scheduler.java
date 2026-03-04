package models;

public class Scheduler extends User {

    private String staffId;

    public Scheduler(String userId, String staffId, String name, String email, String password, String phone) {
        super(userId, name, email, password, phone, "SCHEDULER");
        this.staffId = staffId;
    }

    @Override
    public boolean login(String email, String password) {
        return this.getEmail().equals(email) && this.getPassword().equals(password);
    }

    // getters
    public String getStaffId() {
        return staffId;
    }

    // setters
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    // format: userId|name|email|password|phone|role|staffId
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + staffId;
    }

    // rebuild Scheduler object from a line in users.txt
    public static Scheduler fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Scheduler(parts[0], parts[6], parts[1], parts[2], parts[3], parts[4]);
    }
}
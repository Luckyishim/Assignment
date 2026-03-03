package models;

public class Hall {

    private String hallId;
    private String hallName;
    private String hallType; // AUDITORIUM, BANQUET_HALL, MEETING_ROOM
    private int capacity;
    private double ratePerHour;

    public Hall(String hallId, String hallName, String hallType) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallType = hallType;
        setCapacityAndRate(); // auto set based on type
    }

    // set capacity and rate based on hall type from the question
    private void setCapacityAndRate() {
        if (hallType.equals("AUDITORIUM")) {
            capacity = 1000;
            ratePerHour = 300.0;
        } else if (hallType.equals("BANQUET_HALL")) {
            capacity = 300;
            ratePerHour = 100.0;
        } else if (hallType.equals("MEETING_ROOM")) {
            capacity = 30;
            ratePerHour = 50.0;
        }
    }

    // getters
    public String getHallId() { return hallId; }
    public String getHallName() { return hallName; }
    public String getHallType() { return hallType; }
    public int getCapacity() { return capacity; }
    public double getRatePerHour() { return ratePerHour; }

    // setters
    public void setHallName(String hallName) { this.hallName = hallName; }
    public void setHallType(String hallType) {
        this.hallType = hallType;
        setCapacityAndRate(); // recalculate when type changes
    }

    // format: hallId|hallName|hallType|capacity|ratePerHour
    public String toFileString() {
        return hallId + "|" + hallName + "|" + hallType + "|" + capacity + "|" + ratePerHour;
    }

    // rebuild Hall object from a line in halls.txt
    public static Hall fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Hall(parts[0], parts[1], parts[2]);
    }
}
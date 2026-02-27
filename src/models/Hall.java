package models;

public class Hall {
    private String hallID;
    private String hallName;
    private String hallType;
    private int capacity;
    private double ratePerHour;

    //Parameterized Constructor
    public Hall(String hallID, String hallName, String hallType) {
        this.hallID = hallID;
        this.hallName = hallName;
        this.hallType = hallType;


        assignRules();
    }

    //Assigning different hall types and their information
    private void assignRules() {
        switch (this.hallType.toLowerCase()) {
            case "auditorium":
                this.capacity = 1000;
                this.ratePerHour = 300.00;
                break;
            case "banquet hall":
                this.capacity = 300;
                this.ratePerHour = 100.00;
                break;
            case "meeting room":
                this.capacity = 30;
                this.ratePerHour = 50.00;
                break;
            default:
                this.capacity = 0;
                this.ratePerHour = 0;
                break;
        }
    }

    //Getter and Setter for all fields

    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getHallType() {
        return hallType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
        assignRules();
    }


    //Methods for Halls
    public boolean isAvailable(){
        return true;
    }
}
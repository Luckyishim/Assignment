package models;

import java.time.LocalDateTime;

public class HallSchedule {
    private String scheduleID;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String remarks;

    //Parameterized Constructor
    public HallSchedule(String scheduleID, LocalDateTime start, LocalDateTime end, String remarks) {
        this.scheduleID = scheduleID;
        this.startDateTime = start;
        this.endDateTime = end;
        this.remarks = remarks;
    }
    //Validating the time of business operations.
    public boolean isValid() {
        int start = startDateTime.getHour();
        int end = endDateTime.getHour();

        return (start >= 8 && end <= 18);
    }

    //Getter and Setter for other fields


    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}




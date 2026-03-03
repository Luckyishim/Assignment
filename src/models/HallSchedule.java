package models;

public class HallSchedule {

    private String scheduleId;
    private String hallId;
    private String scheduleType; // AVAILABILITY or MAINTENANCE
    private String startDateTime; // stored as string "dd-MM-yyyy HH:mm"
    private String endDateTime;
    private String remarks;

    public HallSchedule(String scheduleId, String hallId, String scheduleType,
                        String startDateTime, String endDateTime, String remarks) {
        this.scheduleId = scheduleId;
        this.hallId = hallId;
        this.scheduleType = scheduleType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.remarks = remarks;
    }

    // getters
    public String getScheduleId() { return scheduleId; }
    public String getHallId() { return hallId; }
    public String getScheduleType() { return scheduleType; }
    public String getStartDateTime() { return startDateTime; }
    public String getEndDateTime() { return endDateTime; }
    public String getRemarks() { return remarks; }

    // setters
    public void setStartDateTime(String startDateTime) { this.startDateTime = startDateTime; }
    public void setEndDateTime(String endDateTime) { this.endDateTime = endDateTime; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // format: scheduleId|hallId|scheduleType|startDateTime|endDateTime|remarks
    public String toFileString() {
        return scheduleId + "|" + hallId + "|" + scheduleType + "|" + startDateTime + "|" + endDateTime + "|" + remarks;
    }

    // rebuild HallSchedule object from a line in schedules.txt
    public static HallSchedule fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new HallSchedule(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}
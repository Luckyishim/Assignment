package utils;


import models.Hall;
import models.HallSchedule;
import java.util.ArrayList;
import java.util.List;

public class HallFileHandler extends FileHandler {

    private static final String HALLS_FILE = "txt-data/halls.txt";
    private static final String SCHEDULES_FILE = "txt-data/schedules.txt";

    // ---- HALL METHODS ----

    public static void saveHall(Hall hall) {
        appendLine(HALLS_FILE, hall.toFileString());
    }

    public static void updateHall(Hall hall) {
        updateById(HALLS_FILE, hall.getHallId(), hall.toFileString());
    }

    public static void deleteHall(String hallId) {
        deleteById(HALLS_FILE, hallId);
    }

    public static List<Hall> getAllHalls() {
        List<Hall> halls = new ArrayList<>();
        List<String> lines = readAll(HALLS_FILE);
        for (String line : lines) {
            halls.add(Hall.fromFileString(line));
        }
        return halls;
    }

    public static Hall getHallById(String hallId) {
        List<String> lines = readAll(HALLS_FILE);
        for (String line : lines) {
            if (line.startsWith(hallId + "|")) {
                return Hall.fromFileString(line);
            }
        }
        return null;
    }

    // filter halls by type
    public static List<Hall> getHallsByType(String hallType) {
        List<Hall> filtered = new ArrayList<>();
        for (Hall hall : getAllHalls()) {
            if (hall.getHallType().equalsIgnoreCase(hallType)) {
                filtered.add(hall);
            }
        }
        return filtered;
    }

    // ---- SCHEDULE METHODS ----

    public static void saveSchedule(HallSchedule schedule) {
        appendLine(SCHEDULES_FILE, schedule.toFileString());
    }

    public static void updateSchedule(HallSchedule schedule) {
        updateById(SCHEDULES_FILE, schedule.getScheduleId(), schedule.toFileString());
    }

    public static void deleteSchedule(String scheduleId) {
        deleteById(SCHEDULES_FILE, scheduleId);
    }

    public static List<HallSchedule> getAllSchedules() {
        List<HallSchedule> schedules = new ArrayList<>();
        List<String> lines = readAll(SCHEDULES_FILE);
        for (String line : lines) {
            schedules.add(HallSchedule.fromFileString(line));
        }
        return schedules;
    }

    // get all availability schedules for a specific hall
    public static List<HallSchedule> getAvailableSchedulesByHall(String hallId) {
        List<HallSchedule> result = new ArrayList<>();
        for (HallSchedule s : getAllSchedules()) {
            if (s.getHallId().equals(hallId) && s.getScheduleType().equals("AVAILABILITY")) {
                result.add(s);
            }
        }
        return result;
    }

    // get all schedules of type AVAILABILITY (for customer to view)
    public static List<HallSchedule> getAllAvailableSchedules() {
        List<HallSchedule> result = new ArrayList<>();
        for (HallSchedule s : getAllSchedules()) {
            if (s.getScheduleType().equals("AVAILABILITY")) {
                result.add(s);
            }
        }
        return result;
    }
}
package utils;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler extends FileHandler {

    private static final String USERS_FILE = "data/users.txt";

    // save any user to users.txt
    public static void saveUser(User user) {
        appendLine(USERS_FILE, user.toFileString());
    }

    // update existing user in users.txt
    public static void updateUser(User user) {
        updateById(USERS_FILE, user.getUserId(), user.toFileString());
    }

    // delete user by id
    public static void deleteUser(String userId) {
        deleteById(USERS_FILE, userId);
    }

    // get all customers
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            if (line.contains("|CUSTOMER|")) {
                customers.add(Customer.fromFileString(line));
            }
        }
        return customers;
    }

    // get all schedulers
    public static List<Scheduler> getAllSchedulers() {
        List<Scheduler> schedulers = new ArrayList<>();
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            if (line.contains("|SCHEDULER|")) {
                schedulers.add(Scheduler.fromFileString(line));
            }
        }
        return schedulers;
    }

    // throws InvalidLoginException if email or password dont match
    public static User loginUser(String email, String password) throws InvalidLoginException {
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts[2].equals(email) && parts[3].equals(password)) {
                String role = parts[5];
                if (role.equals("CUSTOMER")) return Customer.fromFileString(line);
                if (role.equals("SCHEDULER")) return Scheduler.fromFileString(line);
                if (role.equals("ADMIN")) return Administrator.fromFileString(line);
                if (role.equals("MANAGER")) return Manager.fromFileString(line);
            }
        }
        // no match found - throw our custom exception
        throw new InvalidLoginException("Invalid email or password. Please try again.");
    }

    // check if email already registered
    public static boolean emailExists(String email) {
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts[2].equals(email)) return true;
        }
        return false;
    }

    // find customer by id
    public static Customer getCustomerById(String customerId) {
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            if (line.startsWith(customerId + "|") && line.contains("|CUSTOMER|")) {
                return Customer.fromFileString(line);
            }
        }
        return null;
    }

    // find scheduler by id
    public static Scheduler getSchedulerById(String schedulerId) {
        List<String> lines = readAll(USERS_FILE);
        for (String line : lines) {
            if (line.startsWith(schedulerId + "|") && line.contains("|SCHEDULER|")) {
                return Scheduler.fromFileString(line);
            }
        }
        return null;
    }
}
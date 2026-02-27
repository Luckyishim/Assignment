package utils;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class UserFileHandler extends FileHandler {
    //To load users from users.txt ig
    public List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("users.txt");

        for (String line : lines) {
            String[] parts = line.split("\\|");

            UserRole role = UserRole.valueOf(parts[6]);

            User user;
            if (role == UserRole.ADMIN) {
                String adminID = parts[7];
                user = new Administrator(parts[0], parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]), role, adminID);
            } else if (role == UserRole.CUSTOMER) {
                String address = parts[7];
                user = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]), role, address);
            } else if (role == UserRole.MANAGER) {
                String managerID = parts[7];
                user = new Manager(parts[0], parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]), role, managerID);
            } else {
                String staffID = parts[7];
                user = new Scheduler(parts[0], parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]), role, staffID);
            }
            userList.add(user);
        }
        return userList;
    }

    //Now we make smth to save the users we add
    public void saveUsers(List<User> userList) {
        List<String> lines = new ArrayList<>();

        for (User user : userList) {
            lines.add(user.toFileFormat());
        }

        FileHandler.writeToFile("users.txt", lines);
    }

    //To authenticate our users
    public User authenticate(String inputID, String inputPass) {
        List<User> userList = loadUsers();

        for (User user : userList) {
            if (user.login(inputID, inputPass)) {
                return user;
            }
        }
        return null;
    }
}

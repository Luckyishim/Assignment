package utils;

import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileHandler {

    //Path to the .txt folder
    private static final String FOLDER_PATH = "txt-data/";

    //If folder not found we create
    public static void checkFolder() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    //To write in the .txt giles
    public static void saveToFiles(String filename, String content) {
        checkFolder();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_PATH + filename, true))) {
            writer.write(content);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error in saving the data:" + e.getMessage());
        }
    }


    //To read from the .txt files
    public static List<String> readFromFile(String filename) {
        List<String> data = new ArrayList<>();
        File file = new File(FOLDER_PATH + filename);

        if (!file.exists()) {
            return data;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error in reading the file: " + e.getMessage());
        }
        return data;
    }

    //To write inside the .txt files
    public static void writeToFile(String filename, List<String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FOLDER_PATH + filename))) {
            for (String line : data) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file:" + e.getMessage());
        }
    }

}
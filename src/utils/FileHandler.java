package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// base file handler - all other file handlers use these core methods
public class FileHandler {

    // read all lines from a file, returns empty list if file doesnt exist
    public static List<String> readAll(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(filename);
            if (!file.exists()) return lines; // just return empty if no file yet

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
        return lines;
    }

    // write a single new line at the end of the file
    public static void appendLine(String filename, String data) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs(); // create folders if they dont exist

            // if file exists and doesnt end with newline, fix it first
            // this prevents new entries being glued to the last line
            if (file.exists() && file.length() > 0) {
                java.io.RandomAccessFile raf = new java.io.RandomAccessFile(file, "r");
                raf.seek(file.length() - 1);
                byte lastByte = raf.readByte();
                raf.close();
                if (lastByte != '\n') {
                    BufferedWriter fixer = new BufferedWriter(new FileWriter(file, true));
                    fixer.newLine();
                    fixer.close();
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(data);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    // overwrite the whole file with a new list of lines
    public static void writeAll(String filename, List<String> lines) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    // delete a line where the first field (id) matches the given id
    public static void deleteById(String filename, String id) {
        List<String> lines = readAll(filename);
        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            if (!line.startsWith(id + "|")) {
                updated.add(line);
            }
        }
        writeAll(filename, updated);
    }

    // update a line where the first field (id) matches - replaces whole line
    public static void updateById(String filename, String id, String newData) {
        List<String> lines = readAll(filename);
        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith(id + "|")) {
                updated.add(newData); // replace with new data
            } else {
                updated.add(line);
            }
        }
        writeAll(filename, updated);
    }

    // generate next ID like CUS1, CUS2, CUS3 based on how many lines already in file
    public static String generateId(String filename, String prefix) {
        int count = readAll(filename).size() + 1;
        return prefix + count;
    }

    // check if an id already exists in a file
    public static boolean idExists(String filename, String id) {
        List<String> lines = readAll(filename);
        for (String line : lines) {
            if (line.startsWith(id + "|")) {
                return true;
            }
        }
        return false;
    }
}
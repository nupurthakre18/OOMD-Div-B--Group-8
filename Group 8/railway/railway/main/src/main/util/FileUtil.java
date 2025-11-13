package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtil class for handling file operations
 * Provides methods to read, write, and manage data files
 */
public class FileUtil {
    private static final String DATA_DIR = "src/main/data/";
    public static final String USERS_FILE = DATA_DIR + "users.txt";
    public static final String TRAINS_FILE = DATA_DIR + "trains.txt";
    public static final String BOOKINGS_FILE = DATA_DIR + "bookings.txt";

    /**
     * Initialize data files if they don't exist
     */
    public static void initializeFiles() {
        try {
            File dataDir = new File(DATA_DIR);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // Create users file with default admin
            File usersFile = new File(USERS_FILE);
            if (!usersFile.exists()) {
                usersFile.createNewFile();
                writeToFile(USERS_FILE, "A001,Admin,admin@railway.com,admin123,Admin", false);
                System.out.println("Default admin created: admin@railway.com / admin123");
            }

            // Create trains file with sample data
            File trainsFile = new File(TRAINS_FILE);
            if (!trainsFile.exists()) {
                trainsFile.createNewFile();
                writeToFile(TRAINS_FILE, "101,Mumbai,Delhi,06:30,250,250,500.0", false);
                writeToFile(TRAINS_FILE, "102,Delhi,Jaipur,09:00,300,300,350.0", true);
                writeToFile(TRAINS_FILE, "103,Pune,Mumbai,14:30,200,200,150.0", true);
                System.out.println("Sample trains data initialized.");
            }

            // Create bookings file
            File bookingsFile = new File(BOOKINGS_FILE);
            if (!bookingsFile.exists()) {
                bookingsFile.createNewFile();
            }

        } catch (IOException e) {
            System.err.println("Error initializing files: " + e.getMessage());
        }
    }

    /**
     * Read all lines from a file
     */
    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    /**
     * Write a line to a file
     * @param append - if true, append to file; if false, overwrite
     */
    public static void writeToFile(String filename, String content, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Write multiple lines to a file (overwrite mode)
     */
    public static void writeAllToFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Delete a line from file that matches the given identifier
     */
    public static boolean deleteFromFile(String filename, String identifier) {
        List<String> lines = readFromFile(filename);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            if (!line.startsWith(identifier + ",")) {
                updatedLines.add(line);
            } else {
                found = true;
            }
        }

        if (found) {
            writeAllToFile(filename, updatedLines);
            return true;
        }
        return false;
    }

    /**
     * Update a line in file
     */
    public static boolean updateInFile(String filename, String identifier, String newContent) {
        List<String> lines = readFromFile(filename);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {
            if (line.startsWith(identifier + ",")) {
                updatedLines.add(newContent);
                found = true;
            } else {
                updatedLines.add(line);
            }
        }

        if (found) {
            writeAllToFile(filename, updatedLines);
            return true;
        }
        return false;
    }

    /**
     * Check if a record exists in file
     */
    public static boolean existsInFile(String filename, String identifier) {
        List<String> lines = readFromFile(filename);
        for (String line : lines) {
            if (line.startsWith(identifier + ",")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get next available ID for a given prefix
     */
    public static String generateNextId(String filename, String prefix) {
        List<String> lines = readFromFile(filename);
        int maxId = 0;

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length > 0 && parts[0].startsWith(prefix)) {
                try {
                    int id = Integer.parseInt(parts[0].substring(prefix.length()));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }

        return prefix + String.format("%03d", maxId + 1);
    }
}
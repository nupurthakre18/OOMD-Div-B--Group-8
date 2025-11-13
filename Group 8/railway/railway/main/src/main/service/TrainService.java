package service;

import model.Train;
import util.FileUtil;
import util.InputValidator;
import java.util.ArrayList;
import java.util.List;

/**
 * TrainService handles train-related business logic
 */
public class TrainService {
    
    /**
     * Add a new train
     */
    public boolean addTrain(String trainNumber, String source, String destination,
                           String departureTime, int totalSeats, double baseFare) {
        // Validate inputs
        if (trainNumber == null || trainNumber.trim().isEmpty()) {
            System.out.println("Train number cannot be empty.");
            return false;
        }
        if (source == null || source.trim().isEmpty()) {
            System.out.println("Source cannot be empty.");
            return false;
        }
        if (destination == null || destination.trim().isEmpty()) {
            System.out.println("Destination cannot be empty.");
            return false;
        }
        if (!InputValidator.isValidTime(departureTime)) {
            System.out.println("Invalid time format. Use HH:MM format.");
            return false;
        }
        if (!InputValidator.isPositiveInteger(totalSeats)) {
            System.out.println("Total seats must be positive.");
            return false;
        }
        if (!InputValidator.isPositiveDouble(baseFare)) {
            System.out.println("Base fare must be positive.");
            return false;
        }
        
        // Check if train already exists
        if (trainExists(trainNumber)) {
            System.out.println("Train number already exists.");
            return false;
        }
        
        // Create train object
        Train train = new Train(trainNumber, source, destination, 
                               departureTime, totalSeats, baseFare);
        
        // Save to file
        FileUtil.writeToFile(FileUtil.TRAINS_FILE, train.toFileFormat(), true);
        System.out.println("Train added successfully!");
        return true;
    }
    
    /**
     * Update train details
     */
    public boolean updateTrain(String trainNumber, String departureTime, 
                              int totalSeats, double baseFare) {
        Train train = getTrainByNumber(trainNumber);
        
        if (train == null) {
            System.out.println("Train not found.");
            return false;
        }
        
        // Validate inputs
        if (!InputValidator.isValidTime(departureTime)) {
            System.out.println("Invalid time format.");
            return false;
        }
        if (!InputValidator.isPositiveInteger(totalSeats)) {
            System.out.println("Total seats must be positive.");
            return false;
        }
        if (!InputValidator.isPositiveDouble(baseFare)) {
            System.out.println("Base fare must be positive.");
            return false;
        }
        
        // Update train details
        train.setDepartureTime(departureTime);
        train.setTotalSeats(totalSeats);
        train.setBaseFare(baseFare);
        
        // Adjust available seats if total seats changed
        if (train.getAvailableSeats() > totalSeats) {
            train.setAvailableSeats(totalSeats);
        }
        
        // Update in file
        boolean updated = FileUtil.updateInFile(FileUtil.TRAINS_FILE, 
                                               trainNumber, train.toFileFormat());
        
        if (updated) {
            System.out.println("Train updated successfully!");
        }
        
        return updated;
    }
    
    /**
     * Delete a train
     */
    public boolean deleteTrain(String trainNumber) {
        if (!trainExists(trainNumber)) {
            System.out.println("Train not found.");
            return false;
        }
        
        boolean deleted = FileUtil.deleteFromFile(FileUtil.TRAINS_FILE, trainNumber);
        
        if (deleted) {
            System.out.println("Train deleted successfully!");
        }
        
        return deleted;
    }
    
    /**
     * Search trains by source and destination
     */
    public List<Train> searchTrains(String source, String destination) {
        List<Train> matchingTrains = new ArrayList<>();
        List<String> trains = FileUtil.readFromFile(FileUtil.TRAINS_FILE);
        
        for (String line : trains) {
            Train train = parseTrainFromLine(line);
            if (train != null && 
                train.getSource().equalsIgnoreCase(source) && 
                train.getDestination().equalsIgnoreCase(destination)) {
                matchingTrains.add(train);
            }
        }
        
        return matchingTrains;
    }
    
    /**
     * Get train by number
     */
    public Train getTrainByNumber(String trainNumber) {
        List<String> trains = FileUtil.readFromFile(FileUtil.TRAINS_FILE);
        
        for (String line : trains) {
            if (line.startsWith(trainNumber + ",")) {
                return parseTrainFromLine(line);
            }
        }
        
        return null;
    }
    
    /**
     * Get all trains
     */
    public List<Train> getAllTrains() {
        List<Train> trainList = new ArrayList<>();
        List<String> trains = FileUtil.readFromFile(FileUtil.TRAINS_FILE);
        
        for (String line : trains) {
            Train train = parseTrainFromLine(line);
            if (train != null) {
                trainList.add(train);
            }
        }
        
        return trainList;
    }
    
    /**
     * Check if train exists
     */
    public boolean trainExists(String trainNumber) {
        return FileUtil.existsInFile(FileUtil.TRAINS_FILE, trainNumber);
    }
    
    /**
     * Update train seat availability
     */
    public boolean updateTrainSeats(String trainNumber, int seatsToBook) {
        Train train = getTrainByNumber(trainNumber);
        
        if (train == null) {
            return false;
        }
        
        if (!train.bookSeats(seatsToBook)) {
            System.out.println("Not enough seats available.");
            return false;
        }
        
        return FileUtil.updateInFile(FileUtil.TRAINS_FILE, 
                                    trainNumber, train.toFileFormat());
    }
    
    /**
     * Release train seats (for cancellation)
     */
    public boolean releaseTrainSeats(String trainNumber, int seatsToRelease) {
        Train train = getTrainByNumber(trainNumber);
        
        if (train == null) {
            return false;
        }
        
        train.releaseSeats(seatsToRelease);
        
        return FileUtil.updateInFile(FileUtil.TRAINS_FILE, 
                                    trainNumber, train.toFileFormat());
    }
    
    /**
     * Parse train from file line
     */
    private Train parseTrainFromLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                return new Train(
                    parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    Integer.parseInt(parts[4].trim()),
                    Integer.parseInt(parts[5].trim()),
                    Double.parseDouble(parts[6].trim())
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing train data: " + e.getMessage());
        }
        return null;
    }
}
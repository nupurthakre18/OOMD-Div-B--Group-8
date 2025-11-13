package controller;

import model.Train;
import service.TrainService;
import java.util.List;

/**
 * TrainController handles train-related requests
 */
public class TrainController {
    private TrainService trainService;
    
    public TrainController() {
        this.trainService = new TrainService();
    }
    
    /**
     * Add a new train
     */
    public boolean addTrain(String trainNumber, String source, String destination,
                           String departureTime, int totalSeats, double baseFare) {
        try {
            return trainService.addTrain(trainNumber, source, destination, 
                                        departureTime, totalSeats, baseFare);
        } catch (Exception e) {
            System.err.println("Error adding train: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update train details
     */
    public boolean updateTrain(String trainNumber, String departureTime, 
                              int totalSeats, double baseFare) {
        try {
            return trainService.updateTrain(trainNumber, departureTime, 
                                           totalSeats, baseFare);
        } catch (Exception e) {
            System.err.println("Error updating train: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a train
     */
    public boolean deleteTrain(String trainNumber) {
        try {
            return trainService.deleteTrain(trainNumber);
        } catch (Exception e) {
            System.err.println("Error deleting train: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Search trains by source and destination
     */
    public void searchTrains(String source, String destination) {
        try {
            List<Train> trains = trainService.searchTrains(source, destination);
            
            if (trains.isEmpty()) {
                System.out.println("\nNo trains found for " + source + " to " + destination);
            } else {
                System.out.println("\n=== Available Trains ===");
                for (Train train : trains) {
                    train.displayInfo();
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching trains: " + e.getMessage());
        }
    }
    
    /**
     * View all trains
     */
    public void viewAllTrains() {
        try {
            List<Train> trains = trainService.getAllTrains();
            
            if (trains.isEmpty()) {
                System.out.println("\nNo trains available.");
            } else {
                System.out.println("\n=== All Trains ===");
                for (Train train : trains) {
                    train.displayInfo();
                }
            }
        } catch (Exception e) {
            System.err.println("Error viewing trains: " + e.getMessage());
        }
    }
    
    /**
     * Get train by number
     */
    public Train getTrainByNumber(String trainNumber) {
        try {
            return trainService.getTrainByNumber(trainNumber);
        } catch (Exception e) {
            System.err.println("Error fetching train: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Display train information
     */
    public void displayTrainInfo(String trainNumber) {
        try {
            Train train = trainService.getTrainByNumber(trainNumber);
            if (train != null) {
                System.out.println("\n=== Train Details ===");
                train.displayInfo();
            } else {
                System.out.println("Train not found.");
            }
        } catch (Exception e) {
            System.err.println("Error displaying train info: " + e.getMessage());
        }
    }
}
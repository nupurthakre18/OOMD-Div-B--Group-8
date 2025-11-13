package model;

/**
 * Train class representing train information
 * Demonstrates Encapsulation
 */
public class Train {
    private String trainNumber;
    private String source;
    private String destination;
    private String departureTime;
    private int totalSeats;
    private int availableSeats;
    private double baseFare;

    public Train(String trainNumber, String source, String destination, 
                 String departureTime, int totalSeats, double baseFare) {
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.baseFare = baseFare;
    }

    public Train(String trainNumber, String source, String destination, 
                 String departureTime, int totalSeats, int availableSeats, double baseFare) {
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.baseFare = baseFare;
    }

    // Getters and Setters
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    // Business methods
    public boolean bookSeats(int seats) {
        if (availableSeats >= seats) {
            availableSeats -= seats;
            return true;
        }
        return false;
    }

    public void releaseSeats(int seats) {
        availableSeats += seats;
        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }

    public double calculateFare(int seats, String ticketClass) {
        double multiplier = 1.0;
        switch (ticketClass.toUpperCase()) {
            case "1A":
                multiplier = 3.0;
                break;
            case "2A":
                multiplier = 2.0;
                break;
            case "3A":
                multiplier = 1.5;
                break;
            case "SL":
                multiplier = 1.0;
                break;
        }
        return baseFare * seats * multiplier;
    }

    public void displayInfo() {
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Route: " + source + " -> " + destination);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Available Seats: " + availableSeats + "/" + totalSeats);
        System.out.println("Base Fare: Rs. " + baseFare);
        System.out.println("-----------------------------------");
    }

    public String toFileFormat() {
        return trainNumber + "," + source + "," + destination + "," + 
               departureTime + "," + totalSeats + "," + availableSeats + "," + baseFare;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainNumber='" + trainNumber + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", availableSeats=" + availableSeats +
                ", baseFare=" + baseFare +
                '}';
    }
}
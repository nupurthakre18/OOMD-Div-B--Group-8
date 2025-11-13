package service;

import model.Ticket;
import model.Train;
import util.FileUtil;
import util.InputValidator;
import util.SingletonConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingService handles booking-related business logic
 * Uses Singleton pattern for configuration management
 */
public class BookingService {
    private TrainService trainService;
    private SingletonConnection config;
    
    public BookingService() {
        this.trainService = new TrainService();
        this.config = SingletonConnection.getInstance();
    }
    
    /**
     * Book a ticket
     */
    public boolean bookTicket(String userId, String trainNumber, int seats, String ticketClass) {
        // Validate ticket class
        if (!InputValidator.isValidTicketClass(ticketClass)) {
            System.out.println("Invalid ticket class. Choose from: 1A, 2A, 3A, SL");
            return false;
        }
        
        // Validate seat count
        int maxSeats = config.getMaxSeatsPerBooking();
        if (!InputValidator.isValidSeatCount(seats, maxSeats)) {
            return false;
        }
        
        // Check if train exists
        Train train = trainService.getTrainByNumber(trainNumber);
        if (train == null) {
            System.out.println("Train not found.");
            return false;
        }
        
        // Check seat availability
        if (train.getAvailableSeats() < seats) {
            System.out.println("Only " + train.getAvailableSeats() + " seats available.");
            return false;
        }
        
        // Calculate fare
        double totalFare = train.calculateFare(seats, ticketClass);
        
        // Generate booking ID
        String bookingId = FileUtil.generateNextId(FileUtil.BOOKINGS_FILE, "B");
        
        // Create ticket
        Ticket ticket = new Ticket(bookingId, userId, trainNumber, 
                                   seats, ticketClass, totalFare, "Confirmed");
        
        // Update train seats
        if (!trainService.updateTrainSeats(trainNumber, seats)) {
            System.out.println("Failed to update train seats.");
            return false;
        }
        
        // Save booking
        FileUtil.writeToFile(FileUtil.BOOKINGS_FILE, ticket.toFileFormat(), true);
        
        System.out.println("\n=== Booking Confirmed ===");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Class: " + ticketClass);
        System.out.println("Seats: " + seats);
        System.out.println("Total Fare: Rs. " + totalFare);
        System.out.println("========================\n");
        
        return true;
    }
    
    /**
     * Cancel a booking
     */
    public boolean cancelBooking(String bookingId, String userId) {
        // Check if cancellation is allowed
        if (!config.isCancellationAllowed()) {
            System.out.println("Cancellation is currently not allowed.");
            return false;
        }
        
        // Get booking
        Ticket ticket = getBookingById(bookingId);
        
        if (ticket == null) {
            System.out.println("Booking not found.");
            return false;
        }
        
        // Verify user owns this booking
        if (!ticket.getUserId().equals(userId)) {
            System.out.println("You are not authorized to cancel this booking.");
            return false;
        }
        
        // Check if already cancelled
        if ("Cancelled".equals(ticket.getStatus())) {
            System.out.println("Booking already cancelled.");
            return false;
        }
        
        // Update ticket status
        ticket.cancelTicket();
        
        // Release seats
        trainService.releaseTrainSeats(ticket.getTrainNumber(), ticket.getNumberOfSeats());
        
        // Update booking file
        boolean updated = FileUtil.updateInFile(FileUtil.BOOKINGS_FILE, 
                                               bookingId, ticket.toFileFormat());
        
        if (updated) {
            System.out.println("Booking cancelled successfully!");
            System.out.println("Refund of Rs. " + ticket.getTotalFare() + " will be processed.");
        }
        
        return updated;
    }
    
    /**
     * Get booking by ID
     */
    public Ticket getBookingById(String bookingId) {
        List<String> bookings = FileUtil.readFromFile(FileUtil.BOOKINGS_FILE);
        
        for (String line : bookings) {
            if (line.startsWith(bookingId + ",")) {
                return parseTicketFromLine(line);
            }
        }
        
        return null;
    }
    
    /**
     * Get all bookings for a user
     */
    public List<Ticket> getUserBookings(String userId) {
        List<Ticket> userBookings = new ArrayList<>();
        List<String> bookings = FileUtil.readFromFile(FileUtil.BOOKINGS_FILE);
        
        for (String line : bookings) {
            Ticket ticket = parseTicketFromLine(line);
            if (ticket != null && ticket.getUserId().equals(userId)) {
                userBookings.add(ticket);
            }
        }
        
        return userBookings;
    }
    
    /**
     * Get all bookings
     */
    public List<Ticket> getAllBookings() {
        List<Ticket> allBookings = new ArrayList<>();
        List<String> bookings = FileUtil.readFromFile(FileUtil.BOOKINGS_FILE);
        
        for (String line : bookings) {
            Ticket ticket = parseTicketFromLine(line);
            if (ticket != null) {
                allBookings.add(ticket);
            }
        }
        
        return allBookings;
    }
    
    /**
     * Get booking statistics for a user
     */
    public void displayUserStatistics(String userId) {
        List<Ticket> bookings = getUserBookings(userId);
        int confirmed = 0;
        int cancelled = 0;
        double totalSpent = 0;
        
        for (Ticket ticket : bookings) {
            if ("Confirmed".equals(ticket.getStatus())) {
                confirmed++;
                totalSpent += ticket.getTotalFare();
            } else if ("Cancelled".equals(ticket.getStatus())) {
                cancelled++;
            }
        }
        
        System.out.println("\n=== Your Booking Statistics ===");
        System.out.println("Total Bookings: " + bookings.size());
        System.out.println("Confirmed: " + confirmed);
        System.out.println("Cancelled: " + cancelled);
        System.out.println("Total Spent: Rs. " + totalSpent);
        System.out.println("==============================\n");
    }
    
    /**
     * Parse ticket from file line
     */
    private Ticket parseTicketFromLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 8) {
                return new Ticket(
                    parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim(),
                    Integer.parseInt(parts[3].trim()),
                    parts[4].trim(),
                    Double.parseDouble(parts[5].trim()),
                    parts[6].trim(),
                    parts[7].trim()
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing ticket data: " + e.getMessage());
        }
        return null;
    }
}
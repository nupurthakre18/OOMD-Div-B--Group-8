package controller;

import model.Ticket;
import service.BookingService;
import java.util.List;

/**
 * BookingController handles booking-related requests
 */
public class BookingController {
    private BookingService bookingService;
    
    public BookingController() {
        this.bookingService = new BookingService();
    }
    
    /**
     * Book a ticket
     */
    public boolean bookTicket(String userId, String trainNumber, int seats, String ticketClass) {
        try {
            return bookingService.bookTicket(userId, trainNumber, seats, ticketClass);
        } catch (Exception e) {
            System.err.println("Booking error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cancel a booking
     */
    public boolean cancelBooking(String bookingId, String userId) {
        try {
            return bookingService.cancelBooking(bookingId, userId);
        } catch (Exception e) {
            System.err.println("Cancellation error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * View user bookings
     */
    public void viewUserBookings(String userId) {
        try {
            List<Ticket> bookings = bookingService.getUserBookings(userId);
            
            if (bookings.isEmpty()) {
                System.out.println("\nNo bookings found.");
            } else {
                System.out.println("\n=== Your Bookings ===");
                for (Ticket ticket : bookings) {
                    ticket.displayInfo();
                }
                
                // Display statistics
                bookingService.displayUserStatistics(userId);
            }
        } catch (Exception e) {
            System.err.println("Error viewing bookings: " + e.getMessage());
        }
    }
    
    /**
     * View all bookings (Admin)
     */
    public void viewAllBookings() {
        try {
            List<Ticket> bookings = bookingService.getAllBookings();
            
            if (bookings.isEmpty()) {
                System.out.println("\nNo bookings found.");
            } else {
                System.out.println("\n=== All Bookings ===");
                for (Ticket ticket : bookings) {
                    ticket.displayInfo();
                }
                System.out.println("Total Bookings: " + bookings.size());
            }
        } catch (Exception e) {
            System.err.println("Error viewing all bookings: " + e.getMessage());
        }
    }
    
    /**
     * Get booking by ID
     */
    public Ticket getBookingById(String bookingId) {
        try {
            return bookingService.getBookingById(bookingId);
        } catch (Exception e) {
            System.err.println("Error fetching booking: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Display booking details
     */
    public void displayBookingDetails(String bookingId) {
        try {
            Ticket ticket = bookingService.getBookingById(bookingId);
            if (ticket != null) {
                System.out.println("\n=== Booking Details ===");
                ticket.displayInfo();
            } else {
                System.out.println("Booking not found.");
            }
        } catch (Exception e) {
            System.err.println("Error displaying booking: " + e.getMessage());
        }
    }
}
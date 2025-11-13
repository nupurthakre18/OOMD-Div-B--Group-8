package model;

/**
 * Ticket class representing booking information
 * Demonstrates Encapsulation
 */
public class Ticket {
    private String bookingId;
    private String userId;
    private String trainNumber;
    private int numberOfSeats;
    private String ticketClass;
    private double totalFare;
    private String status; // Confirmed, Cancelled
    private String bookingDate;

    public Ticket(String bookingId, String userId, String trainNumber, 
                  int numberOfSeats, String ticketClass, double totalFare, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.numberOfSeats = numberOfSeats;
        this.ticketClass = ticketClass;
        this.totalFare = totalFare;
        this.status = status;
        this.bookingDate = java.time.LocalDate.now().toString();
    }

    public Ticket(String bookingId, String userId, String trainNumber, 
                  int numberOfSeats, String ticketClass, double totalFare, 
                  String status, String bookingDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.numberOfSeats = numberOfSeats;
        this.ticketClass = ticketClass;
        this.totalFare = totalFare;
        this.status = status;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    // Business methods
    public void cancelTicket() {
        this.status = "Cancelled";
    }

    public boolean isConfirmed() {
        return "Confirmed".equals(status);
    }

    public void displayInfo() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Class: " + ticketClass);
        System.out.println("Seats: " + numberOfSeats);
        System.out.println("Total Fare: Rs. " + totalFare);
        System.out.println("Status: " + status);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("-----------------------------------");
    }

    public String toFileFormat() {
        return bookingId + "," + userId + "," + trainNumber + "," + 
               numberOfSeats + "," + ticketClass + "," + totalFare + "," + 
               status + "," + bookingDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "bookingId='" + bookingId + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", totalFare=" + totalFare +
                ", status='" + status + '\'' +
                '}';
    }
}
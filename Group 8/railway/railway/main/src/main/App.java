package main;

import controller.BookingController;
import controller.TrainController;
import controller.UserController;
import model.User;
import util.FileUtil;
import java.util.Scanner;

/**
 * Main entry point for Railway Ticket Booking System
 * Provides menu-based console interface for passengers and admins
 */
public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static UserController userController = new UserController();
    private static TrainController trainController = new TrainController();
    private static BookingController bookingController = new BookingController();

    public static void main(String[] args) {
        // Initialize data files
        FileUtil.initializeFiles();
        
        System.out.println("========================================");
        System.out.println("  RAILWAY TICKET BOOKING SYSTEM");
        System.out.println("========================================\n");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    handlePassengerLogin();
                    break;
                case 2:
                    handleAdminLogin();
                    break;
                case 3:
                    handlePassengerRegistration();
                    break;
                case 4:
                    running = false;
                    System.out.println("\nThank you for using Railway Ticket Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Railway Ticket Booking System ===");
        System.out.println("1. Login as Passenger");
        System.out.println("2. Login as Admin");
        System.out.println("3. Register as New Passenger");
        System.out.println("4. Exit");
        System.out.println("=====================================");
    }

    private static void handlePassengerLogin() {
        System.out.println("\n--- Passenger Login ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        User user = userController.login(email, password, "Passenger");
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName());
            passengerMenu(user);
        } else {
            System.out.println("Invalid credentials or not a passenger account!");
        }
    }

    private static void handleAdminLogin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        User user = userController.login(email, password, "Admin");
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName());
            adminMenu(user);
        } else {
            System.out.println("Invalid credentials or not an admin account!");
        }
    }

    private static void handlePassengerRegistration() {
        System.out.println("\n--- New Passenger Registration ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if (userController.registerPassenger(name, email, password)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed! Email might already exist.");
        }
    }

    private static void passengerMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n=== Passenger Menu ===");
            System.out.println("1. Search Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Logout");
            System.out.println("======================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    searchTrains();
                    break;
                case 2:
                    bookTicket(user);
                    break;
                case 3:
                    viewMyBookings(user);
                    break;
                case 4:
                    cancelTicket(user);
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void adminMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add New Train");
            System.out.println("2. Update Train");
            System.out.println("3. Delete Train");
            System.out.println("4. View All Trains");
            System.out.println("5. View All Bookings");
            System.out.println("6. Logout");
            System.out.println("==================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewTrain();
                    break;
                case 2:
                    updateTrain();
                    break;
                case 3:
                    deleteTrain();
                    break;
                case 4:
                    viewAllTrains();
                    break;
                case 5:
                    viewAllBookings();
                    break;
                case 6:
                    loggedIn = false;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void searchTrains() {
        System.out.println("\n--- Search Trains ---");
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        
        trainController.searchTrains(source, destination);
    }

    private static void bookTicket(User user) {
        System.out.println("\n--- Book Ticket ---");
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        int seats = getIntInput("Enter Number of Seats: ");
        System.out.print("Enter Class (1A/2A/3A/SL): ");
        String ticketClass = scanner.nextLine();
        
        bookingController.bookTicket(user.getUserId(), trainNumber, seats, ticketClass);
    }

    private static void viewMyBookings(User user) {
        System.out.println("\n--- My Bookings ---");
        bookingController.viewUserBookings(user.getUserId());
    }

    private static void cancelTicket(User user) {
        System.out.println("\n--- Cancel Ticket ---");
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine();
        
        bookingController.cancelBooking(bookingId, user.getUserId());
    }

    private static void addNewTrain() {
        System.out.println("\n--- Add New Train ---");
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Departure Time (HH:MM): ");
        String departureTime = scanner.nextLine();
        int totalSeats = getIntInput("Enter Total Seats: ");
        double fare = getDoubleInput("Enter Base Fare: ");
        
        trainController.addTrain(trainNumber, source, destination, departureTime, totalSeats, fare);
    }

    private static void updateTrain() {
        System.out.println("\n--- Update Train ---");
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter New Departure Time (HH:MM): ");
        String departureTime = scanner.nextLine();
        int totalSeats = getIntInput("Enter New Total Seats: ");
        double fare = getDoubleInput("Enter New Base Fare: ");
        
        trainController.updateTrain(trainNumber, departureTime, totalSeats, fare);
    }

    private static void deleteTrain() {
        System.out.println("\n--- Delete Train ---");
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        
        trainController.deleteTrain(trainNumber);
    }

    private static void viewAllTrains() {
        System.out.println("\n--- All Trains ---");
        trainController.viewAllTrains();
    }

    private static void viewAllBookings() {
        System.out.println("\n--- All Bookings ---");
        bookingController.viewAllBookings();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
package util;

import java.util.regex.Pattern;

/**
 * InputValidator class for validating user inputs
 * Demonstrates encapsulation of validation logic
 */
public class InputValidator {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern TIME_PATTERN = 
        Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 4) {
            System.out.println("Password must be at least 4 characters long.");
            return false;
        }
        return true;
    }
    
    /**
     * Validate name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (name.length() < 2) {
            System.out.println("Name must be at least 2 characters long.");
            return false;
        }
        return true;
    }
    
    /**
     * Validate time format (HH:MM)
     */
    public static boolean isValidTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            return false;
        }
        return TIME_PATTERN.matcher(time).matches();
    }
    
    /**
     * Validate positive integer
     */
    public static boolean isPositiveInteger(int value) {
        return value > 0;
    }
    
    /**
     * Validate positive double
     */
    public static boolean isPositiveDouble(double value) {
        return value > 0.0;
    }
    
    /**
     * Validate ticket class
     */
    public static boolean isValidTicketClass(String ticketClass) {
        if (ticketClass == null) {
            return false;
        }
        String upper = ticketClass.toUpperCase();
        return upper.equals("1A") || upper.equals("2A") || 
               upper.equals("3A") || upper.equals("SL");
    }
    
    /**
     * Validate seat count
     */
    public static boolean isValidSeatCount(int seats, int maxSeats) {
        if (seats <= 0) {
            System.out.println("Number of seats must be positive.");
            return false;
        }
        if (seats > maxSeats) {
            System.out.println("Cannot book more than " + maxSeats + " seats at once.");
            return false;
        }
        return true;
    }
    
    /**
     * Sanitize input string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("[,;]", "");
    }
}
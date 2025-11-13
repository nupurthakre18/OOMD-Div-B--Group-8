package controller;

import model.User;
import service.UserService;

/**
 * UserController handles user-related requests
 * Acts as intermediary between presentation and service layer
 */
public class UserController {
    private UserService userService;
    
    public UserController() {
        this.userService = new UserService();
    }
    
    /**
     * Handle user login
     */
    public User login(String email, String password, String userType) {
        try {
            return userService.authenticateUser(email, password, userType);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Handle passenger registration
     */
    public boolean registerPassenger(String name, String email, String password) {
        try {
            return userService.registerUser(name, email, password, "Passenger");
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Handle admin registration (if needed)
     */
    public boolean registerAdmin(String name, String email, String password) {
        try {
            return userService.registerUser(name, email, password, "Admin");
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(String userId) {
        try {
            return userService.getUserById(userId);
        } catch (Exception e) {
            System.err.println("Error fetching user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Update user password
     */
    public boolean updatePassword(String userId, String newPassword) {
        try {
            return userService.updatePassword(userId, newPassword);
        } catch (Exception e) {
            System.err.println("Password update error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Display user information
     */
    public void displayUserInfo(String userId) {
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                user.displayInfo();
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.err.println("Error displaying user info: " + e.getMessage());
        }
    }
}
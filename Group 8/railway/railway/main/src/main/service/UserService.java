package service;

import model.User;
import util.FactoryUserCreator;
import util.FileUtil;
import util.InputValidator;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService handles user-related business logic
 * Separates business logic from data access and presentation
 */
public class UserService {
    
    /**
     * Authenticate user and return User object if valid
     */
    public User authenticateUser(String email, String password, String userType) {
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return null;
        }
        
        List<String> users = FileUtil.readFromFile(FileUtil.USERS_FILE);
        
        for (String line : users) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String storedEmail = parts[2].trim();
                String storedPassword = parts[3].trim();
                String storedType = parts[4].trim();
                
                if (storedEmail.equals(email) && 
                    storedPassword.equals(password) && 
                    storedType.equals(userType)) {
                    return FactoryUserCreator.createUserFromFileData(line);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Register a new passenger
     */
    public boolean registerUser(String name, String email, String password, String userType) {
        // Validate inputs
        if (!InputValidator.isValidName(name)) {
            return false;
        }
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (!InputValidator.isValidPassword(password)) {
            return false;
        }
        
        // Check if email already exists
        if (emailExists(email)) {
            System.out.println("Email already registered.");
            return false;
        }
        
        // Generate new user ID
        String userId = FileUtil.generateNextId(FileUtil.USERS_FILE, 
                                                userType.equals("Admin") ? "A" : "P");
        
        // Create user object
        User user = FactoryUserCreator.createUser(userId, name, email, password, userType);
        
        if (user != null) {
            // Save to file
            FileUtil.writeToFile(FileUtil.USERS_FILE, user.toFileFormat(), true);
            System.out.println("User registered successfully with ID: " + userId);
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if email already exists
     */
    public boolean emailExists(String email) {
        List<String> users = FileUtil.readFromFile(FileUtil.USERS_FILE);
        
        for (String line : users) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[2].trim().equals(email)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(String userId) {
        List<String> users = FileUtil.readFromFile(FileUtil.USERS_FILE);
        
        for (String line : users) {
            if (line.startsWith(userId + ",")) {
                return FactoryUserCreator.createUserFromFileData(line);
            }
        }
        
        return null;
    }
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        List<String> users = FileUtil.readFromFile(FileUtil.USERS_FILE);
        
        for (String line : users) {
            User user = FactoryUserCreator.createUserFromFileData(line);
            if (user != null) {
                userList.add(user);
            }
        }
        
        return userList;
    }
    
    /**
     * Update user password
     */
    public boolean updatePassword(String userId, String newPassword) {
        if (!InputValidator.isValidPassword(newPassword)) {
            return false;
        }
        
        User user = getUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            return FileUtil.updateInFile(FileUtil.USERS_FILE, userId, user.toFileFormat());
        }
        
        return false;
    }
}
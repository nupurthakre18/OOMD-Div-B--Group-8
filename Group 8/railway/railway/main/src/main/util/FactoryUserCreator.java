package util;

import model.Admin;
import model.Passenger;
import model.User;

/**
 * Factory pattern implementation for creating User objects
 * Demonstrates Factory Design Pattern
 */
public class FactoryUserCreator {
    
    /**
     * Create a User object based on user type
     * @param userType - "Admin" or "Passenger"
     * @return User object (Admin or Passenger)
     */
    public static User createUser(String userId, String name, String email, 
                                   String password, String userType) {
        if (userType == null || userType.isEmpty()) {
            return null;
        }

        switch (userType.trim()) {
            case "Admin":
                return new Admin(userId, name, email, password);
            case "Passenger":
                return new Passenger(userId, name, email, password);
            default:
                System.err.println("Invalid user type: " + userType);
                return null;
        }
    }

    /**
     * Create User from file data string
     * @param fileData - CSV format: userId,name,email,password,userType
     */
    public static User createUserFromFileData(String fileData) {
        try {
            String[] parts = fileData.split(",");
            if (parts.length >= 5) {
                return createUser(parts[0].trim(), parts[1].trim(), 
                                parts[2].trim(), parts[3].trim(), parts[4].trim());
            }
        } catch (Exception e) {
            System.err.println("Error parsing user data: " + e.getMessage());
        }
        return null;
    }

    /**
     * Validate user type
     */
    public static boolean isValidUserType(String userType) {
        return "Admin".equals(userType) || "Passenger".equals(userType);
    }
}
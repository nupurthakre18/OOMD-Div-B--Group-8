package model;

/**
 * Admin class extending User
 * Demonstrates Inheritance and Polymorphism
 */
public class Admin extends User {

    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password, "Admin");
    }

    /**
     * Overridden method from User class (Polymorphism)
     */
    @Override
    public void displayInfo() {
        System.out.println("=== Admin Information ===");
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Type: " + getUserType());
        System.out.println("========================");
    }

    /**
     * Additional method specific to Admin
     */
    public boolean canManageTrains() {
        return true;
    }

    public boolean canViewAllBookings() {
        return true;
    }
}
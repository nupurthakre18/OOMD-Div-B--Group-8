package model;

/**
 * Passenger class extending User
 * Demonstrates Inheritance and Polymorphism
 */
public class Passenger extends User {

    public Passenger(String userId, String name, String email, String password) {
        super(userId, name, email, password, "Passenger");
    }

    /**
     * Overridden method from User class (Polymorphism)
     */
    @Override
    public void displayInfo() {
        System.out.println("=== Passenger Information ===");
        System.out.println("Passenger ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Type: " + getUserType());
        System.out.println("============================");
    }

    /**
     * Additional method specific to Passenger
     */
    public boolean canBookTicket() {
        return true;
    }
}
package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton pattern implementation for managing application-wide resources
 * Demonstrates Singleton Design Pattern
 * This simulates a connection manager that could be used for caching or session management
 */
public class SingletonConnection {
    
    // Single instance
    private static SingletonConnection instance;
    
    // Session data storage
    private Map<String, Object> sessionData;
    
    // Configuration settings
    private Map<String, String> config;
    
    /**
     * Private constructor to prevent instantiation
     */
    private SingletonConnection() {
        sessionData = new HashMap<>();
        config = new HashMap<>();
        initializeConfig();
    }
    
    /**
     * Thread-safe lazy initialization
     */
    public static synchronized SingletonConnection getInstance() {
        if (instance == null) {
            instance = new SingletonConnection();
            System.out.println("SingletonConnection initialized.");
        }
        return instance;
    }
    
    /**
     * Initialize default configuration
     */
    private void initializeConfig() {
        config.put("MAX_BOOKINGS_PER_USER", "10");
        config.put("MAX_SEATS_PER_BOOKING", "6");
        config.put("CANCELLATION_ALLOWED", "true");
        config.put("APP_VERSION", "1.0");
    }
    
    /**
     * Store session data
     */
    public void setSessionData(String key, Object value) {
        sessionData.put(key, value);
    }
    
    /**
     * Retrieve session data
     */
    public Object getSessionData(String key) {
        return sessionData.get(key);
    }
    
    /**
     * Clear session data
     */
    public void clearSession() {
        sessionData.clear();
    }
    
    /**
     * Get configuration value
     */
    public String getConfig(String key) {
        return config.getOrDefault(key, "");
    }
    
    /**
     * Set configuration value
     */
    public void setConfig(String key, String value) {
        config.put(key, value);
    }
    
    /**
     * Get maximum bookings allowed per user
     */
    public int getMaxBookingsPerUser() {
        return Integer.parseInt(config.getOrDefault("MAX_BOOKINGS_PER_USER", "10"));
    }
    
    /**
     * Get maximum seats per booking
     */
    public int getMaxSeatsPerBooking() {
        return Integer.parseInt(config.getOrDefault("MAX_SEATS_PER_BOOKING", "6"));
    }
    
    /**
     * Check if cancellation is allowed
     */
    public boolean isCancellationAllowed() {
        return Boolean.parseBoolean(config.getOrDefault("CANCELLATION_ALLOWED", "true"));
    }
    
    /**
     * Display configuration
     */
    public void displayConfig() {
        System.out.println("=== System Configuration ===");
        for (Map.Entry<String, String> entry : config.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("===========================");
    }
}
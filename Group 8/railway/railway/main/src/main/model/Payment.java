package model;

/**
 * Payment class for handling payment processing
 * Demonstrates Encapsulation and business logic
 */
public class Payment {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;

    public Payment(String paymentId, String bookingId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
        this.paymentDate = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Business methods
    public boolean processPayment() {
        // Simulate payment processing
        System.out.println("Processing payment of Rs. " + amount + " via " + paymentMethod);
        this.paymentStatus = "Success";
        return true;
    }

    public void refundPayment() {
        if ("Success".equals(paymentStatus)) {
            this.paymentStatus = "Refunded";
            System.out.println("Payment of Rs. " + amount + " has been refunded.");
        }
    }

    public void displayInfo() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Status: " + paymentStatus);
        System.out.println("Date: " + paymentDate);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
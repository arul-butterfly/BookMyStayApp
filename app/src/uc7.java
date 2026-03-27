/**
 * Book My Stay Application
 *
 * Use Case 7: Add-On Service Selection
 * Demonstrates Map + List for attaching services to reservations.
 *
 * @author Arul
 * @version 7.1
 */

import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------------- ADD-ON SERVICE ----------------
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(serviceName + " (₹" + price + ")");
    }
}

// ---------------- SERVICE MANAGER ----------------
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        System.out.println("\nAdd-On Services for Reservation: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// ---------------- MAIN CLASS ----------------
public class uc7 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v7.1");
        System.out.println("=======================================");

        // Create Reservation
        Reservation r1 = new Reservation("R101", "Arul", "Single Room");

        // Create Services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService pickup = new AddOnService("Airport Pickup", 500);

        // Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services to reservation
        manager.addService(r1.getReservationId(), wifi);
        manager.addService(r1.getReservationId(), breakfast);
        manager.addService(r1.getReservationId(), pickup);

        // Display services and cost
        manager.displayServices(r1.getReservationId());

        System.out.println("\nAdd-On services added successfully.");
    }
}
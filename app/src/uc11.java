/**
 * Book My Stay Application
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * Demonstrates multi-threading and synchronization.
 *
 * @author Arul
 * @version 11.1
 */

import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------------- THREAD-SAFE INVENTORY ----------------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // synchronized method → critical section
    public synchronized boolean bookRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName()
                    + " booking room... Available: " + available);

            // simulate delay (race condition scenario)
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " SUCCESS → Remaining: " + (available - 1));

            return true;
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " FAILED → No rooms available");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// ---------------- BOOKING TASK (THREAD) ----------------
class BookingTask implements Runnable {

    private Reservation reservation;
    private RoomInventory inventory;

    public BookingTask(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        inventory.bookRoom(reservation.getRoomType());
    }
}

// ---------------- MAIN CLASS ----------------
public class uc11{

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v11.1");
        System.out.println("=======================================");

        // Shared Inventory
        RoomInventory inventory = new RoomInventory();

        // Simulate multiple users
        Thread t1 = new Thread(new BookingTask(new Reservation("Arul", "Single Room"), inventory), "User-1");
        Thread t2 = new Thread(new BookingTask(new Reservation("Rahul", "Single Room"), inventory), "User-2");
        Thread t3 = new Thread(new BookingTask(new Reservation("Priya", "Single Room"), inventory), "User-3");

        // Start threads (concurrent execution)
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {}

        // Final inventory
        inventory.displayInventory();

        System.out.println("\nThread-safe booking completed.");
    }
}
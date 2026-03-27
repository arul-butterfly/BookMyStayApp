/**
 * Book My Stay Application
 *
 * Use Case 12: Data Persistence & System Recovery
 * Demonstrates Serialization & Deserialization for saving state.
 *
 * @author Arul
 * @version 12.1
 */

import java.io.*;
import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation implements Serializable {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room: " + roomType +
                " | Room ID: " + roomId);
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void displayInventory() {
        System.out.println("\nInventory State:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// ---------------- SYSTEM STATE ----------------
class SystemState implements Serializable {
    List<Reservation> bookings;
    RoomInventory inventory;

    public SystemState(List<Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// ---------------- PERSISTENCE SERVICE ----------------
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("\nSystem state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("\nNo previous data found. Starting fresh...");
            return null;
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class uc12 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v12.1");
        System.out.println("=======================================");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        RoomInventory inventory;

        if (state != null) {
            // Restore state
            bookings = state.bookings;
            inventory = state.inventory;

            System.out.println("\nRecovered Bookings:");
            for (Reservation r : bookings) {
                r.display();
            }

        } else {
            // Fresh start
            bookings = new ArrayList<>();
            inventory = new RoomInventory();

            bookings.add(new Reservation("Arul", "Single Room", "SI1"));
            bookings.add(new Reservation("Rahul", "Double Room", "DO1"));
        }

        // Display inventory
        inventory.displayInventory();

        // Save state before exit
        PersistenceService.save(new SystemState(bookings, inventory));

        System.out.println("\nSystem ready with persistent state.");
    }
}
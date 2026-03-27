/**
 * Book My Stay Application
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates Queue + Set + HashMap for safe booking and allocation.
 *
 * @author Arul
 * @version 6.1
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

// ---------------- INVENTORY ----------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\n---- Updated Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// ---------------- BOOKING QUEUE ----------------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO removal
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------------- BOOKING SERVICE ----------------
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processBookings(BookingQueue queue, RoomInventory inventory) {

        System.out.println("\n---- Processing Bookings ----");

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                // Generate unique Room ID
                String roomId = type.replace(" ", "").substring(0, 2).toUpperCase() + roomCounter++;

                // Ensure uniqueness using Set
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type → allocated IDs
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // Reduce inventory
                    inventory.reduceAvailability(type);

                    System.out.println("Booking Confirmed for " + r.getGuestName()
                            + " | Room Type: " + type
                            + " | Room ID: " + roomId);
                }

            } else {
                System.out.println("Booking Failed for " + r.getGuestName()
                        + " | No rooms available for " + type);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\n---- Room Allocations ----");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class uc6 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v6.1");
        System.out.println("=======================================");

        // Queue
        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Arul", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room")); // should fail
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking Service
        BookingService service = new BookingService();

        // Process bookings
        service.processBookings(queue, inventory);

        // Show allocations
        service.displayAllocations();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely.");
    }
}
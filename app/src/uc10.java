/**
 * Book My Stay Application
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Demonstrates Stack (LIFO) for rollback and safe cancellation.
 *
 * @author Arul
 * @version 10.1
 */

import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\n---- Current Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// ---------------- BOOKING HISTORY ----------------
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addBooking(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String id) {
        return bookings.get(id);
    }

    public void removeBooking(String id) {
        bookings.remove(id);
    }

    public boolean exists(String id) {
        return bookings.containsKey(id);
    }
}

// ---------------- CANCELLATION SERVICE ----------------
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validate
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Get booking
        Reservation r = history.getBooking(reservationId);

        // Push room ID to stack (LIFO rollback)
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Remove booking
        history.removeBooking(reservationId);

        System.out.println("Cancellation Successful!");
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\n---- Rollback Stack (LIFO) ----");
        System.out.println(rollbackStack);
    }
}

// ---------------- MAIN CLASS ----------------
public class uc10{

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v10.1");
        System.out.println("=======================================");

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addBooking(new Reservation("R101", "Single Room", "SI1"));
        history.addBooking(new Reservation("R102", "Double Room", "DO1"));

        // Cancellation Service
        CancellationService service = new CancellationService();

        // Cancel booking
        service.cancelBooking("R101", history, inventory);

        // Try invalid cancellation
        service.cancelBooking("R999", history, inventory);

        // Show rollback stack
        service.showRollbackStack();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nSystem rollback handled safely.");
    }
}
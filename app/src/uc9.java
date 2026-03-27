/**
 * Book My Stay Application
 *
 * Use Case 9: Error Handling & Validation
 * Demonstrates validation, custom exceptions, and fail-fast design.
 *
 * @author Arul
 * @version 9.1
 */

import java.util.*;

// ---------------- CUSTOM EXCEPTION ----------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {
        int count = getAvailability(roomType);

        if (count < 0) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (count == 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        inventory.put(roomType, count - 1);
    }
}

// ---------------- VALIDATOR ----------------
class BookingValidator {

    public static void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Validate availability
        if (inventory.getAvailability(roomType) == 0) {
            throw new InvalidBookingException("Selected room is not available.");
        }
    }
}

// ---------------- BOOKING SERVICE ----------------
class BookingService {

    private int counter = 1;

    public void bookRoom(String guestName, String roomType, RoomInventory inventory) {

        try {
            // Validate first (Fail-Fast)
            BookingValidator.validate(guestName, roomType, inventory);

            // Allocate room
            String roomId = roomType.substring(0, 2).toUpperCase() + counter++;
            inventory.reduceAvailability(roomType);

            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Room ID: " + roomId);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class uc9{

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v9.1");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Valid booking
        service.bookRoom("Arul", "Single Room", inventory);

        // Invalid room type
        service.bookRoom("Rahul", "Deluxe Room", inventory);

        // No availability
        service.bookRoom("Priya", "Double Room", inventory);

        // Empty name
        service.bookRoom("", "Suite Room", inventory);

        System.out.println("\nSystem running safely after handling errors.");
    }
}

import java.util.*;

// ---------------- RESERVATION CLASS ----------------
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// ---------------- BOOKING QUEUE ----------------
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation); // FIFO insertion
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests
    public void displayQueue() {
        System.out.println("\n---- Booking Request Queue ----");

        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class uc5 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v5.1");
        System.out.println("=======================================\n");

        // Create Queue
        BookingQueue bookingQueue = new BookingQueue();

        // Add booking requests (FIFO)
        bookingQueue.addRequest(new Reservation("Arul", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));

        // Display Queue
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored successfully (No allocation done).");
    }
}
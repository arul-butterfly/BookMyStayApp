/**
 * Book My Stay Application
 *
 * Use Case 8: Booking History & Reporting
 * Demonstrates List for storing booking history and reporting.
 *
 * @author Arul
 * @version 8.1
 */

import java.util.*;

// ---------------- RESERVATION ----------------
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}

// ---------------- BOOKING HISTORY ----------------
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addBooking(Reservation reservation) {
        history.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\n---- Booking History ----");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }
}

// ---------------- REPORT SERVICE ----------------
class BookingReportService {

    // Generate simple report
    public void generateReport(List<Reservation> history) {

        System.out.println("\n---- Booking Report ----");

        if (history.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        Map<String, Integer> report = new HashMap<>();

        // Count bookings per room type
        for (Reservation r : history) {
            report.put(r.getRoomType(),
                    report.getOrDefault(r.getRoomType(), 0) + 1);
        }

        // Display report
        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue() + " bookings");
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class uc8 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - v8.1");
        System.out.println("=======================================");

        // Booking History
        BookingHistory history = new BookingHistory();

        // Simulating confirmed bookings
        history.addBooking(new Reservation("Arul", "Single Room", "SI1"));
        history.addBooking(new Reservation("Rahul", "Double Room", "DO1"));
        history.addBooking(new Reservation("Priya", "Single Room", "SI2"));
        history.addBooking(new Reservation("Kiran", "Suite Room", "SU1"));

        // Display history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllBookings());

        System.out.println("\nReporting completed successfully.");
    }
}
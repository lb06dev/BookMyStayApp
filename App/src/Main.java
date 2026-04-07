import java.util.ArrayList;
import java.util.List;

// Use Case 8: Booking History & Reporting
// Represents a confirmed reservation
class Reservation {
    private String bookingId;
    private String guestName;
    private String roomType;

    public Reservation(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + " | Guest: " + guestName + " | Room: " + roomType;
    }
}

// Booking History Component
class BookingHistory {
    // List preserves insertion order, suitable for chronological records
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation res) {
        history.add(res);
    }

    public List<Reservation> getHistory() {
        return history;
    }
}

// Reporting Service Component
class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("--- Confirmed Booking Report ---");
        for (Reservation res : history.getHistory()) {
            System.out.println(res);
        }
        System.out.println("--------------------------------");
    }
}

public class Main{
    public static void main(String[] args) {
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed bookings
        bookingHistory.addReservation(new Reservation("B001", "Alice", "Deluxe"));
        bookingHistory.addReservation(new Reservation("B002", "Bob", "Suite"));
        bookingHistory.addReservation(new Reservation("B003", "Charlie", "Standard"));

        // Admin reviews booking history
        reportService.generateReport(bookingHistory);
    }
}
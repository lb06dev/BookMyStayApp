
import java.util.*;

// Class to handle Booking Cancellation and Inventory Rollback
class BookingManager {
    // Inventory count for room type
    private int availableRooms;
    // Stack to keep track of recently released Room IDs for LIFO rollback
    private Stack<String> releasedRoomIds;
    // Map to simulate database of confirmed bookings (BookingID -> RoomID)
    private Map<Integer, String> confirmedBookings;

    public BookingManager(int initialInventory) {
        this.availableRooms = initialInventory;
        this.releasedRoomIds = new Stack<>();
        this.confirmedBookings = new HashMap<>();
        
        // Pre-populate some bookings for simulation
        confirmedBookings.put(101, "ROOM_A");
        confirmedBookings.put(102, "ROOM_B");
    }

    // Use Case 10: Cancel Booking and Rollback Inventory
    public void cancelBooking(int bookingId) {
        System.out.println("\n--- Initiating Cancellation for Booking: " + bookingId + " ---");

        // 1. Validation: Check if reservation exists
        if (!confirmedBookings.containsKey(bookingId)) {
            System.out.println("Error: Booking " + bookingId + " not found or already cancelled.");
            return;
        }

        // 2. Retrieve allocated room
        String roomId = confirmedBookings.get(bookingId);
        System.out.println("Allocated Room found: " + roomId);

        // 3. Rollback Structure: Track released room
        releasedRoomIds.push(roomId);
        System.out.println("Room " + roomId + " added to rollback stack.");

        // 4. Inventory Restoration: Increment count
        availableRooms++;
        System.out.println("Inventory restored. Current available rooms: " + availableRooms);

        // 5. Update History: Remove from confirmed bookings
        confirmedBookings.remove(bookingId);
        System.out.println("Booking " + bookingId + " cancelled successfully.");
    }

    public void displayState() {
        System.out.println("\n[System State] Available Rooms: " + availableRooms + ", Confirmed Bookings: " + confirmedBookings.size());
    }
}

public class Main{
    public static void main(String[] args) {
        // Initialize with 5 rooms
        BookingManager manager = new BookingManager(5);
        manager.displayState();

        // Perform cancellations
        manager.cancelBooking(101); // Valid cancellation
        manager.cancelBooking(102); // Valid cancellation
        manager.cancelBooking(999); // Invalid cancellation

        manager.displayState();
    }
}

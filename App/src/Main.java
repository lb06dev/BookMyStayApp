import java.util.ArrayList;
import java.util.List;

// 1. Custom Exceptions for explicit error handling
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class Room {
    private String roomType;
    private int inventory;

    public Room(String roomType, int inventory) {
        this.roomType = roomType;
        this.inventory = inventory;
    }

    public String getRoomType() { return roomType; }
    public int getInventory() { return inventory; }

    // Guarding System State
    public void bookRoom(int count) throws BookingException {
        if (count <= 0) {
            throw new BookingException("Booking count must be positive.");
        }
        if (inventory - count < 0) {
            throw new BookingException("Insufficient inventory for " + roomType);
        }
        inventory -= count;
        System.out.println(count + " " + roomType + "(s) booked successfully.");
    }
}

class BookingValidator {
    public static void validateBooking(String roomType, int count, List<Room> rooms) throws BookingException {
        // Input Validation
        if (roomType == null || roomType.isEmpty()) {
            throw new BookingException("Room type cannot be empty.");
        }

        Room targetRoom = null;
        for (Room r : rooms) {
            if (r.getRoomType().equalsIgnoreCase(roomType)) {
                targetRoom = r;
                break;
            }
        }

        if (targetRoom == null) {
            throw new BookingException("Invalid Room Type: " + roomType);
        }

        // Check Inventory before processing
        if (targetRoom.getInventory() < count) {
            throw new BookingException("Requested " + count + " " + roomType + "(s), but only " + targetRoom.getInventory() + " available.");
        }
    }
}

public class Main{
    public static void main(String[] args) {
        List<Room> hotelInventory = new ArrayList<>();
        hotelInventory.add(new Room("Deluxe", 2));
        hotelInventory.add(new Room("Suite", 1));

        System.out.println("--- Starting Book My Stay: Use Case 9 ---");

        String[][] guestRequests = {
            {"Deluxe", "1"}, // Valid
            {"Suite", "1"},  // Valid
            {"Deluxe", "5"}, // Invalid - Insufficient
            {"Villa", "1"},  // Invalid - Type
            {"Suite", "-1"}  // Invalid - Negative
        };

        for (String[] request : guestRequests) {
            String roomType = request[0];
            int count = Integer.parseInt(request[1]);
            
            try {
                System.out.println("\nProcessing: " + roomType + " x" + count);
                // Fail-Fast Design: Validate before booking
                BookingValidator.validateBooking(roomType, count, hotelInventory);
                
                // If valid, book
                for (Room r : hotelInventory) {
                    if (r.getRoomType().equalsIgnoreCase(roomType)) {
                        r.bookRoom(count);
                    }
                }
            } catch (BookingException e) {
                // Graceful Failure Handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
    }
}
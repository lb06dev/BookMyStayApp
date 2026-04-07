import java.util.*;

// Domain Model: Represents the details of a Room
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getAmenities() { return amenities; }

    @Override
    public String toString() {
        return String.format("[%s] - Price: $%.2f | Amenities: %s", type, price, amenities);
    }
}

// Inventory Layer: Holds the state of available room counts
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    // Read-only access to availability
    public int getAvailableCount(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return availability.keySet();
    }
}

// Search Service: Handles the logic for filtering and displaying rooms
class SearchService {
    private Inventory inventory;
    private List<Room> roomDefinitions;

    public SearchService(Inventory inventory, List<Room> roomDefinitions) {
        this.inventory = inventory;
        this.roomDefinitions = roomDefinitions;
    }

    public void displayAvailableRooms() {
        System.out.println("--- Searching for Available Rooms ---");
        boolean found = false;

        for (Room room : roomDefinitions) {
            // Validation Logic: Retrieve count from inventory (Read-only)
            int count = inventory.getAvailableCount(room.getType());

            // Defensive Programming: Only display rooms with availability > 0
            if (count > 0) {
                System.out.println(room.toString() + " | Rooms Left: " + count);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, no rooms are currently available.");
        }
        System.out.println("-------------------------------------\n");
    }
}

// Main Class to execute Use Case 4
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        // 1. Setup Data (System State)
        Inventory hotelInventory = new Inventory();
        hotelInventory.addRoomType("Deluxe", 5);
        hotelInventory.addRoomType("Suite", 0); // Out of stock
        hotelInventory.addRoomType("Single", 2);

        List<Room> roomDetails = Arrays.asList(
                new Room("Deluxe", 150.0, "King Bed, WiFi, Mini Bar"),
                new Room("Suite", 300.0, "Ocean View, Jacuzzi, Breakfast"),
                new Room("Single", 80.0, "Twin Bed, WiFi")
        );

        // 2. Initialize Search Service
        SearchService searchService = new SearchService(hotelInventory, roomDetails);

        // 3. Guest performs search
        // Note: This operation will filter out 'Suite' because availability is 0
        searchService.displayAvailableRooms();

        // 4. Verification of System State (Read-only check)
        System.out.println("System Check: Search complete. Inventory state remains unchanged.");
    }
}
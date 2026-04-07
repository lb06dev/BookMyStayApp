import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 1. Mark entities as Serializable to allow file-based storage
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookingId;
    private String guestName;

    public Booking(String bookingId, String guestName) {
        this.bookingId = bookingId;
        this.guestName = guestName;
    }

    @Override
    public String toString() {
        return "Booking[ID=" + bookingId + ", Guest=" + guestName + "]";
    }
}

class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private int availableRooms;

    public Inventory(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public void setAvailableRooms(int rooms) { this.availableRooms = rooms; }
    
    @Override
    public String toString() {
        return "Inventory[Available Rooms=" + availableRooms + "]";
    }
}

// 2. State wrapper to bundle all critical data for persistence
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Booking> bookings;
    private Inventory inventory;

    public SystemState(List<Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public List<Booking> getBookings() { return bookings; }
    public Inventory getInventory() { return inventory; }
}

// 3. Persistence Service to handle Save/Restore operations
class PersistenceService {
    private final String FILE_NAME = "system_state.ser";

    public void saveState(List<Booking> bookings, Inventory inventory) {
        System.out.println("\n[System] Preparing for shutdown. Serializing state...");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            SystemState state = new SystemState(bookings, inventory);
            oos.writeObject(state);
            System.out.println("[Persistence Service] Data written successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("[Error] Failed to save state: " + e.getMessage());
        }
    }

    public SystemState loadState() {
        System.out.println("\n[System] Restarting. Attempting to restore state...");
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[Persistence Service] No existing state file found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("[Persistence Service] Data restored successfully.");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Error] Corrupted or incompatible state file. Recovery failed.");
            return null;
        }
    }
}

// 4. Main Application Execution
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();
        
        // --- System Startup / Restore ---
        SystemState restoredState = persistence.loadState();
        
        List<Booking> bookingHistory;
        Inventory currentInventory;

        if (restoredState != null) {
            bookingHistory = restoredState.getBookings();
            currentInventory = restoredState.getInventory();
        } else {
            // Initializing with default state if no recovery file exists
            bookingHistory = new ArrayList<>();
            currentInventory = new Inventory(10);
            System.out.println("[System] Initialized with default settings.");
        }

        // Show current status after recovery
        System.out.println("Current Status: " + currentInventory);
        System.out.println("Booking Count: " + bookingHistory.size());

        // --- Simulate some system activity ---
        if (bookingHistory.isEmpty()) {
            System.out.println("\n[Action] Adding a new booking...");
            bookingHistory.add(new Booking("BK001", "John Doe"));
            currentInventory.setAvailableRooms(9);
        }

        // --- System Shutdown / Save ---
        persistence.saveState(bookingHistory, currentInventory);
        System.out.println("[System] Application terminated safely.");
    }
}
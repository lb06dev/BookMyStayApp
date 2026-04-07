import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 1. Shared Inventory Management with Thread Safety
class HotelInventory {
    private AtomicInteger availableRooms;

    public HotelInventory(int totalRooms) {
        this.availableRooms = new AtomicInteger(totalRooms);
    }

    // Synchronized method to ensure atomic room booking
    public synchronized boolean bookRoom(String guestName) {
        if (availableRooms.get() > 0) {
            // Simulate processing time
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            
            int remaining = availableRooms.decrementAndGet();
            System.out.println(Thread.currentThread().getName() + " - SUCCESS: Room booked for " + guestName + ". Rooms left: " + remaining);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " - FAILED: No rooms left for " + guestName);
            return false;
        }
    }

    public int getAvailableRooms() {
        return availableRooms.get();
    }
}

// 2. Runnable Task representing a Booking Request
class BookingRequest implements Runnable {
    private HotelInventory inventory;
    private String guestName;

    public BookingRequest(HotelInventory inventory, String guestName) {
        this.inventory = inventory;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        inventory.bookRoom(guestName);
    }
}

// 3. Main Simulation Class
public class Main{
    public static void main(String[] args) {
        System.out.println("--- Starting Concurrent Booking Simulation ---\n");
        
        // Scenario: 10 guests trying to book 5 rooms simultaneously
        HotelInventory hotel = new HotelInventory(5);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            Runnable request = new BookingRequest(hotel, "Guest-" + i);
            executor.execute(request);
        }

        // Shut down executor gracefully
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\n--- Simulation Finished ---");
        System.out.println("Final Room Count: " + hotel.getAvailableRooms());
    }
}
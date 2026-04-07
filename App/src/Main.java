import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a booking request
class Reservation {
    private String guestName;
    private String roomType;
    int roomNumber;


    Reservation(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    void confirmReservation() {
        System.out.println("Reservation Confirmed!");
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Number: " + roomNumber);
    }

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

    @Override
    public String toString() {
        return "Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

public class UC5BookingReq {

    public static void main(String[] args) {

        // Booking Request Queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulating booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Adding requests to queue
        bookingQueue.add(r1);
        bookingQueue.add(r2);
        bookingQueue.add(r3);

        System.out.println("Booking Requests Received and Added to Queue:\n");

        // Display queued requests without allocating rooms
        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nRequests are stored in FIFO order and waiting for allocation.");
    }
}
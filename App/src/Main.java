import java.util.*;

class Room {
    int roomNumber;
    boolean isAvailable;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }
}

public class main {

    static List<Room> rooms = new ArrayList<>();

    public static void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }
    }

    public static Room allocateRoom() {
        for (Room room : rooms) {
            if (room.isAvailable) {
                room.isAvailable = false;
                return room;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeRooms();

        System.out.print("Enter Guest Name: ");
        String guestName = sc.nextLine();

        Room room = allocateRoom();

        if (room != null) {
            Reservation reservation = new Reservation(guestName, room.roomNumber);
            reservation.confirmReservation();
        } else {
            System.out.println("Sorry! No rooms available.");
        }

        sc.close();
    }
}
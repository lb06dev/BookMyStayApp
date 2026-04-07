import java.util.*;

// Main App
public class Main{

    public static void main(String[] args) {

        // Sample reservation IDs
        String reservation1 = "R101";
        String reservation2 = "R102";

        // Create services
        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 500);
        Service parking = new Service("Parking", 300);

        // Manager
        ServiceManager manager = new ServiceManager();

        // Guest selects services
        manager.addService(reservation1, wifi);
        manager.addService(reservation1, breakfast);

        manager.addService(reservation2, parking);
        manager.addService(reservation2, wifi);

        // Display services
        System.out.println("Services for " + reservation1 + ":");
        manager.displayServices(reservation1);

        System.out.println("Total Add-On Cost: " + manager.calculateTotalCost(reservation1));

        System.out.println("\nServices for " + reservation2 + ":");
        manager.displayServices(reservation2);

        System.out.println("Total Add-On Cost: " + manager.calculateTotalCost(reservation2));
    }
}

// Add-On Service Class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

// Manager Class
class ServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.getName() + " : " + s.getCost());
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}
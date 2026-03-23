import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void decrement(String type) throws Exception {
        int available = inventory.getOrDefault(type, 0);
        if (available <= 0) {
            throw new Exception("No rooms available for: " + type);
        }
        inventory.put(type, available - 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> activeReservations = new HashMap<>();
    private Stack<String> cancelledReservations = new Stack<>();

    public void addReservation(Reservation r) {
        activeReservations.put(r.getReservationId(), r);
        System.out.println("Reservation confirmed: " + r);
    }

    public boolean exists(String reservationId) {
        return activeReservations.containsKey(reservationId);
    }

    public Reservation cancelReservation(String reservationId) {
        if (!activeReservations.containsKey(reservationId)) {
            return null;
        }
        Reservation r = activeReservations.remove(reservationId);
        cancelledReservations.push(reservationId);
        System.out.println("Reservation cancelled: " + r);
        return r;
    }

    public Collection<Reservation> getActiveReservations() {
        return activeReservations.values();
    }
}

// Cancellation Service
class CancellationService {
    private InventoryService inventory;
    private BookingHistory history;

    public CancellationService(InventoryService inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancel(String reservationId) {
        if (!history.exists(reservationId)) {
            System.out.println("Cannot cancel: Reservation ID " + reservationId + " does not exist.");
            return;
        }

        Reservation r = history.cancelReservation(reservationId);
        inventory.increment(r.getRoomType());
        System.out.println("Inventory updated: " + r.getRoomType() + " now has " + inventory.getAvailability(r.getRoomType()) + " rooms available.\n");
    }
}

// Main Class
public class UseCase10BookingCancellation {
    public static void main(String[] args) throws Exception {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Simulate bookings
        Reservation r1 = new Reservation("RES001", "Alice", "Single");
        Reservation r2 = new Reservation("RES002", "Bob", "Double");

        inventory.decrement(r1.getRoomType());
        history.addReservation(r1);

        inventory.decrement(r2.getRoomType());
        history.addReservation(r2);

        System.out.println("\n--- Active Reservations Before Cancellation ---");
        for (Reservation r : history.getActiveReservations()) {
            System.out.println(r);
        }

        // Cancel a booking
        System.out.println("\n--- Cancelling RES001 ---");
        cancellationService.cancel("RES001");

        // Attempt to cancel non-existent reservation
        System.out.println("\n--- Cancelling RES003 (Invalid) ---");
        cancellationService.cancel("RES003");

        System.out.println("\n--- Active Reservations After Cancellation ---");
        for (Reservation r : history.getActiveReservations()) {
            System.out.println(r);
        }
    }
}
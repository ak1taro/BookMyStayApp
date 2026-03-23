import java.util.*;

// Custom Exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

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
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Inventory service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        return inventory.get(type);
    }

    public void decrement(String type) throws InvalidBookingException {
        int available = getAvailability(type);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }
        inventory.put(type, available - 1);
    }
}

// Booking service with validation
class BookingService {
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(Reservation reservation) {
        try {
            // Validate room type
            if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
                throw new InvalidBookingException("Room type cannot be empty");
            }

            // Validate guest name
            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            // Check availability and allocate
            inventory.decrement(reservation.getRoomType());

            // Confirm booking
            System.out.println("Booking successful: " + reservation);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        BookingService bookingService = new BookingService(inventory);

        // Test cases
        bookingService.bookRoom(new Reservation("Alice", "Single")); // valid
        bookingService.bookRoom(new Reservation("Bob", "Double"));    // valid
        bookingService.bookRoom(new Reservation("Charlie", "Double")); // should fail, no room left
        bookingService.bookRoom(new Reservation("", "Single"));       // should fail, invalid guest
        bookingService.bookRoom(new Reservation("Diana", "Suite"));   // should fail, invalid room type
    }
}
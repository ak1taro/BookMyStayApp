import java.util.*;

// Reservation (from UC5 queue)
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
}

// Inventory Service (state holder)
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service (allocation logic)
class BookingService {
    private Queue<Reservation> queue;
    private InventoryService inventory;

    // Track allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → assigned room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    // Process booking requests (FIFO)
    public void processBookings() {

        while (!queue.isEmpty()) {
            Reservation r = queue.poll(); // dequeue

            String type = r.getRoomType();

            System.out.println("\nProcessing booking for: " + r.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(type);

                // Store in global set
                allocatedRoomIds.add(roomId);

                // Map to room type
                roomAllocations
                        .computeIfAbsent(type, k -> new HashSet<>())
                        .add(roomId);

                // Decrement inventory
                inventory.decrement(type);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for: " + type);
            }
        }
    }

    // Ensure unique room ID
    private String generateRoomId(String type) {
        String roomId;
        do {
            roomId = type.substring(0, 1).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}

// Main Class
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        // Step 1: Create booking queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single"));
        queue.add(new Reservation("Bob", "Single"));
        queue.add(new Reservation("Charlie", "Suite"));

        // Step 2: Setup inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Suite", 1);

        // Step 3: Process bookings
        BookingService service = new BookingService(queue, inventory);
        service.processBookings();
    }
}
import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------------------------------
 * CLASS - RoomInventory
 * ---------------------------------------------------------------------------
 * * Use Case 3: Centralized Room Inventory Management
 * * Description:
 * This class manages hotel room availability using a HashMap.
 * It provides a centralized way to track how many rooms
 * of each type are available.
 * * @version 3.0
 */
class RoomInventory {

    /** HashMap to store Room Type (String) and Availability (Integer) */
    private Map<String, Integer> inventory;

    /**
     * Constructor to initialize the inventory HashMap.
     */
    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Initializes availability for a specific room type.
     * @param roomType The name of the room type
     * @param count Initial number of available rooms
     */
    public void initializeAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Retrieves the current availability for a room type.
     * @param roomType The name of the room type
     * @return Number of rooms available
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Updates the availability (e.g., after a booking or cancellation).
     * @param roomType The name of the room type
     * @param newCount The updated count
     */
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }
}
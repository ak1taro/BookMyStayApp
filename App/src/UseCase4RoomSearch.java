/**
 * ===========================================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ===========================================================================
 * * Use Case 4: Room Search & Availability Check
 * * Description:
 * This class demonstrates how guests
 * can view available rooms without
 * modifying inventory data.
 * * The system enforces read-only access
 * by design and usage discipline.
 * * @version 4.0
 */
public class UseCase4RoomSearch {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Initialize Room Definitions (Domain Objects)
        Room single = new Room("Single", 1, 250, 1500.0);
        Room doubleRm = new Room("Double", 2, 400, 2500.0);
        Room suite = new Room("Suite", 3, 750, 5000.0);

        // Initialize Inventory and set availability
        RoomInventory inventory = new RoomInventory();
        inventory.addInventory("Single", 5);
        inventory.addInventory("Double", 3);
        inventory.addInventory("Suite", 2);

        // Initialize Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform Search
        searchService.searchAvailableRooms(inventory, single, doubleRm, suite);
    }
}
/**
 * ---------------------------------------------------------------------------
 * MAIN CLASS - UseCase3InventorySetup
 * ---------------------------------------------------------------------------
 * * Use Case 3: Centralized Room Inventory Management
 * * Description:
 * This class demonstrates how to use the RoomInventory
 * class to manage room counts centrally instead of
 * using independent variables.
 * * @version 3.0
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {
        System.out.println("Centralized Room Inventory Management\n");

        // 1. Initialize the Centralized Inventory
        RoomInventory hotelInventory = new RoomInventory();

        // 2. Register Room Types with Counts
        hotelInventory.initializeAvailability("Single Room", 5);
        hotelInventory.initializeAvailability("Double Room", 3);
        hotelInventory.initializeAvailability("Suite Room", 2);

        // 3. Display current inventory state
        System.out.println("Current Inventory Status:");
        System.out.println("Single Rooms available: " + hotelInventory.getAvailability("Single Room"));
        System.out.println("Double Rooms available: " + hotelInventory.getAvailability("Double Room"));
        System.out.println("Suite Rooms available: " + hotelInventory.getAvailability("Suite Room"));

        System.out.println("\nSystem initialized with centralized state.");
    }
}
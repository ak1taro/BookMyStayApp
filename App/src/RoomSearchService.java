import java.util.Map;

/**
 * ---------------------------------------------------------------------------
 * CLASS - RoomSearchService
 * ===========================================================================
 * * Use Case 4: Room Search & Availability Check
 * * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 * * It reads room availability from inventory
 * and room details from Room objects.
 * * No inventory mutation or booking logic
 * is performed in this class.
 * * @version 4.0
 */
public class RoomSearchService {

    /**
     * Displays available rooms along with
     * their details and pricing.
     * * This method performs read-only access
     * to inventory and room data.
     * * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        // Check and display Single Room availability
        if (availability.get("Single") > 0) {
            displayRoomDetails(singleRoom, availability.get("Single"));
        }

        // Check and display Double Room availability
        if (availability.get("Double") > 0) {
            displayRoomDetails(doubleRoom, availability.get("Double"));
        }

        // Check and display Suite Room availability
        if (availability.get("Suite") > 0) {
            displayRoomDetails(suiteRoom, availability.get("Suite"));
        }
    }

    private void displayRoomDetails(Room room, int count) {
        System.out.println(room.getType() + " Room:");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sqft");
        System.out.println("Price per night: " + room.getPrice());
        System.out.println("Available: " + count);
        System.out.println();
    }
}
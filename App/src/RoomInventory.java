import java.util.HashMap;
import java.util.Map;

public class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        // Returns a copy or the map for read-only purposes in this context
        return new HashMap<>(inventory);
    }
}
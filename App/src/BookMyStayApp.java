import java.util.*;
import java.util.concurrent.*;

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

// Thread-safe Inventory Service
class InventoryService {
    private final Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void incrementRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public synchronized int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Booking queue processor
class BookingProcessor implements Runnable {
    private final Queue<Reservation> bookingQueue;
    private final InventoryService inventory;

    public BookingProcessor(Queue<Reservation> bookingQueue, InventoryService inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation res;
            synchronized (bookingQueue) {
                res = bookingQueue.poll();
            }
            if (res == null) {
                break; // queue empty
            }

            boolean allocated = inventory.allocateRoom(res.getRoomType());
            if (allocated) {
                System.out.println(Thread.currentThread().getName() + " booked " + res);
            } else {
                System.out.println(Thread.currentThread().getName() + " failed to book " + res + " (No availability)");
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 3);
        inventory.addRoom("Double", 2);

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Single"));
        bookingQueue.add(new Reservation("Charlie", "Double"));
        bookingQueue.add(new Reservation("Diana", "Double"));
        bookingQueue.add(new Reservation("Eve", "Single")); // last Single room

        int numThreads = 3; // Simulate 3 guests processing concurrently
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n--- Final Inventory ---");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
    }
}
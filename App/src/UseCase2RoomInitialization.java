/**
 * ---------------------------------------------------------------------------
 * MAIN CLASS - UseCase2RoomInitialization
 * ---------------------------------------------------------------------------
 * * Use Case 2: Basic Room Types & Static Availability
 * * Description:
 * This class demonstrates room initialization
 * using domain models before introducing
 * centralized inventory management.
 * * Availability is represented using
 * simple variables to highlight limitations.
 * * @version 2.0
 */
public class UseCase2RoomInitialization {

    /**
     * Application entry point.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");

        // Initialize Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Single Room details
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        // Display Double Room details
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        // Display Suite Room details
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}
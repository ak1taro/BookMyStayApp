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

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> confirmedBookings = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        confirmedBookings.add(r);
        System.out.println("Added to booking history: " + r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedBookings); // read-only
    }
}

// Reporting Service
class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("\n--- Booking Report ---");
        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        Map<String, Integer> roomSummary = new HashMap<>();

        for (Reservation r : reservations) {
            System.out.println(r);
            roomSummary.put(r.getRoomType(), roomSummary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\n--- Room Type Summary ---");
        for (Map.Entry<String, Integer> entry : roomSummary.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " bookings");
        }
    }
}

// Main Class
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed reservations
        history.addReservation(new Reservation("RES001", "Alice", "Single"));
        history.addReservation(new Reservation("RES002", "Bob", "Double"));
        history.addReservation(new Reservation("RES003", "Charlie", "Suite"));
        history.addReservation(new Reservation("RES004", "Diana", "Single"));

        // Admin requests report
        reportService.generateReport(history);
    }
}
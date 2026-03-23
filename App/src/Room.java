public class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}
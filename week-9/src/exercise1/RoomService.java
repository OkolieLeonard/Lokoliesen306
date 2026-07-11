package exercise1;
public class RoomService {
    public boolean isAvailable(String roomType) { System.out.println("Checking: " + roomType); return true; }
    public void book(String roomType, String guest) { System.out.println("Booked: " + roomType + " for " + guest); }
}

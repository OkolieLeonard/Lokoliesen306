package exercise1;

/**
 * Exercise 1 - BookingFacade
 * Problems solved:
 * 1. Tight coupling - client no longer depends on 4 subsystems directly.
 * 2. Duplication - booking logic lives in one place and is reusable.
 * 3. Hard to test - tests only need to mock BookingFacade, not 4 subsystems.
 */
public class BookingFacade {
    private final RoomService rooms;
    private final PaymentService payment;
    private final LoyaltyPoints loyalty;
    private final EmailService email;

    public BookingFacade() {
        this.rooms   = new RoomService();
        this.payment = new PaymentService();
        this.loyalty = new LoyaltyPoints();
        this.email   = new EmailService();
    }

    public boolean bookRoom(String guest, String roomType, double price) {
        if (!rooms.isAvailable(roomType)) { System.out.println("Room not available"); return false; }
        if (!payment.charge(guest, price)) { System.out.println("Payment declined"); return false; }
        rooms.book(roomType, guest);
        loyalty.addPoints(guest, (int) price);
        email.sendConfirmation(guest, roomType);
        System.out.println("Booking confirmed");
        return true;
    }

    public static void main(String[] args) {
        BookingFacade booking = new BookingFacade();
        booking.bookRoom("john@example.com", "DELUXE", 250.00);
    }
}

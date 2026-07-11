package exercise3;

/**
 * Exercise 3 - LegacyOrderFacade
 *
 * Wraps LegacyOrderProcessor via composition.
 * Legacy class is NOT modified - only new code added here.
 * Provides a clean placeOrder() method hiding the messy legacy internals.
 */
public class LegacyOrderFacade {
    private final LegacyOrderProcessor processor;

    public LegacyOrderFacade() {
        this.processor = new LegacyOrderProcessor();
    }

    public void placeOrder(String customerEmail, String itemCode, double amount, String deliveryAddress) {
        processor.processOrder(customerEmail, itemCode, amount, deliveryAddress);
    }

    public static void main(String[] args) {
        LegacyOrderFacade facade = new LegacyOrderFacade();
        facade.placeOrder("charlie@example.com", "KEYBOARD", 79.99, "789 Pine Rd");
    }
}

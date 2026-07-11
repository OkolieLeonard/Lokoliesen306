package exercise2;

/**
 * Main Exercise + Exercise 2 Extension - CheckoutFacade
 *
 * Rollback rules:
 *   - Payment fails  -> no reservation, return failure.
 *   - Shipping fails -> refund payment + release inventory, return failure.
 *   - Success        -> return tracking number + success message.
 *
 * Extension (Ex 2):
 *   - TaxCalculator: CA=8%, others=0%. Only facade changes, client code unchanged.
 *   - Logger: records every attempt with timestamp, userId, outcome.
 */
public class CheckoutFacade {
    private final Inventory     inventory;
    private final Payment       payment;
    private final Shipping      shipping;
    private final Email         email;
    private final TaxCalculator tax;
    private final Logger        logger;

    public CheckoutFacade() {
        this.inventory = new Inventory();
        this.payment   = new Payment();
        this.shipping  = new Shipping();
        this.email     = new Email();
        this.tax       = new TaxCalculator();
        this.logger    = new Logger();
    }

    public OrderResult checkout(String userId, String productId, double price, String address, String state) {
        if (!inventory.checkStock(productId)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Out of stock: " + productId);
        }
        double taxAmount = tax.calculate(price, state);
        double total = price + taxAmount;

        if (!payment.charge(userId, total)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Payment failed");
        }
        inventory.reserve(productId);

        if (!shipping.isAvailable()) {
            payment.refund(userId, total);
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Shipping unavailable - order rolled back");
        }

        String label = shipping.createLabel(address);
        shipping.schedulePickup(label);
        email.send(userId, "Order Confirmed",
                "Your order is on the way! Total: $" + String.format("%.2f", total)
                + " (incl. $" + String.format("%.2f", taxAmount) + " tax). Tracking: " + label);
        logger.log(userId, true);
        return new OrderResult(true, label, "Order placed successfully");
    }

    public static void main(String[] args) {
        CheckoutFacade facade = new CheckoutFacade();
        System.out.println("=== CA order (8% tax) ===");
        System.out.println(facade.checkout("alice@example.com", "LAPTOP", 999.99, "123 Main St", "CA"));
        System.out.println("\n=== Non-CA order (0% tax) ===");
        System.out.println(facade.checkout("bob@example.com", "MOUSE", 29.99, "456 Oak Ave", "TX"));
    }
}

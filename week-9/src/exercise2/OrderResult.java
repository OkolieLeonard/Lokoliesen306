package exercise2;
public class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.success = success; this.trackingNumber = trackingNumber; this.message = message;
    }
    public boolean isSuccess() { return success; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "OrderResult{success=" + success + ", tracking=" + trackingNumber + ", message='" + message + "'}";
    }
}

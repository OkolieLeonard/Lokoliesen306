package exercise3;
class Inventory {
    boolean checkStock(String id) { return true; }
    void reserve(String id) { System.out.println("Reserved " + id); }
}
class Payment {
    boolean charge(String user, double amount) { return true; }
}
class Shipping {
    String createLabel(String address) { return "TRK" + System.currentTimeMillis(); }
    void schedulePickup(String label) { System.out.println("Pickup: " + label); }
}
class Email {
    void send(String to, String subject, String body) { System.out.println("Email to " + to); }
}

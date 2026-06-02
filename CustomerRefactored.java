public class CustomerRefactored {

    // Named constants to eliminate magic numbers
    private static final int DISCOUNT_TYPE_STANDARD = 1;
    private static final int DISCOUNT_TYPE_PREMIUM = 2;
    private static final double DISCOUNT_RATE_STANDARD = 0.1;
    private static final double DISCOUNT_RATE_PREMIUM = 0.2;

    // A cohesive Customer class to encapsulate attributes and eliminate long parameters
    public static class Customer {
        private final String name;
        private final String address;
        private final int customerType;
        private final String email;
        private final boolean isVip;

        public Customer(String name, String address, int customerType, String email, boolean isVip) {
            this.name = name;
            this.address = address;
            this.customerType = customerType;
            this.email = email;
            this.isVip = isVip;
        }

        public String getName() { return name; }
        public String getAddress() { return address; }
        public int getCustomerType() { return customerType; }
        public String getEmail() { return email; }
        public boolean isVip() { return isVip; }
    }

    /**
     * Refactored Orchestration Routine.
     * High functional cohesion: coordinates sub-routines cleanly.
     * Returns the computed total since Java passes double primitives by value.
     */
    public double processCustomer(Customer customer, double[] orders, int orderCount) {
        // 1. Input Validation
        validateInputs(orders, orderCount, customer.getCustomerType());

        // 2. Calculations (Noun functions)
        double totalSum = calculateSum(orders, orderCount);
        double discountRate = determineDiscountRate(customer.getCustomerType());
        double absoluteTotal = calculateTotal(totalSum, discountRate);

        // 3. Notifications and Actions (Verb+Object procedures)
        String standardMessage = formatCustomerMessage(customer, absoluteTotal);
        printMessage(standardMessage);

        if (customer.getEmail() != null) {
            sendEmail(customer.getEmail(), standardMessage);
        }

        // Return the modified absolute total to solve the primitive pass-by-value limitation
        return absoluteTotal;
    }

    // --- Sub-Routines with strict Functional Cohesion ---

    private void validateInputs(double[] orders, int orderCount, int customerType) {
        if (orders == null || orderCount > orders.length || orderCount < 0) {
            throw new IllegalArgumentException("Invalid order count or orders array.");
        }
        for (int i = 0; i < orderCount; i++) {
            if (orders[i] < 0) {
                throw new IllegalArgumentException("Order amounts cannot be negative.");
            }
        }
        if (customerType != DISCOUNT_TYPE_STANDARD && customerType != DISCOUNT_TYPE_PREMIUM) {
            throw new IllegalArgumentException("Invalid customer discount type.");
        }
    }

    private double calculateSum(double[] orders, int count) {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += orders[i];
        }
        return sum;
    }

    private double determineDiscountRate(int customerType) {
        if (customerType == DISCOUNT_TYPE_STANDARD) return DISCOUNT_RATE_STANDARD;
        if (customerType == DISCOUNT_TYPE_PREMIUM) return DISCOUNT_RATE_PREMIUM;
        return 0.0;
    }

    private double calculateTotal(double sum, double discountRate) {
        return sum - (sum * discountRate);
    }

    private String formatCustomerMessage(Customer customer, double total) {
        String msg = "Hello " + customer.getName() + " of " + customer.getAddress() + ", your total is " + total;
        if (customer.isVip()) {
            msg += " (VIP)";
        }
        return msg;
    }

    private void printMessage(String msg) {
        System.out.println(msg);
    }

    // Mock implementation of standard email dependency
    private void sendEmail(String email, String message) {
        System.out.println("[Email Sent to " + email + "]");
    }
}

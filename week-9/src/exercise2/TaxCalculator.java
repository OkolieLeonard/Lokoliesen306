package exercise2;
public class TaxCalculator {
    public double calculate(double price, String state) {
        double rate = "CA".equalsIgnoreCase(state) ? 0.08 : 0.0;
        return price * rate;
    }
}

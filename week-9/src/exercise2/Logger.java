package exercise2;
import java.time.LocalDateTime;
public class Logger {
    public void log(String userId, boolean success) {
        System.out.println("[LOG] " + LocalDateTime.now() + " | user=" + userId + " | " + (success ? "SUCCESS" : "FAIL"));
    }
}

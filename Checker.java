import java.util.*;
import java.util.regex.*;

public class Checker{

    // Common passwords (expand as needed)
    static Set<String> commonPasswords = new HashSet<>(Arrays.asList(
        "123456", "password", "123456789", "qwerty", "abc123",
        "111111", "123123", "admin", "welcome", "iloveyou"
    ));

    // Password history to detect reuse
    static Set<String> passwordHistory = new HashSet<>();

    public static String checkStrength(String password) {
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Check reuse
        if (passwordHistory.contains(password)) {
            return " Reused Password - Choose something new!";
        }

        // Check common password
        if (commonPasswords.contains(password.toLowerCase())) {
            return " Very Weak: Commonly used password!";
        }

        // Length
        if (password.length() >= 8) score += 2;
        else feedback.append(" Use at least 8 characters.\n");

        if (password.length() >= 12) score += 2;

        // Character types
        if (Pattern.compile("[A-Z]").matcher(password).find()) score++;
        else feedback.append(" Add at least one uppercase letter.\n");

        if (Pattern.compile("[a-z]").matcher(password).find()) score++;
        else feedback.append(" Add at least one lowercase letter.\n");

        if (Pattern.compile("[0-9]").matcher(password).find()) score++;
        else feedback.append(" Add at least one digit.\n");

        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) score += 2;
        else feedback.append(" Add at least one special character (e.g., !@#$%).\n");

        // Entropy estimation (simple version)
        double entropy = Math.log(Math.pow(94, password.length())) / Math.log(2);
        if (entropy > 60) score++;

        passwordHistory.add(password); // Store for reuse detection

        // Strength label
        String strengthLabel;
        if (score <= 3) strengthLabel = " Weak";
        else if (score <= 6) strengthLabel = " Moderate";
        else strengthLabel = "Strong";

        return String.format("%s (Score: %d/10)\n%s", strengthLabel, score, feedback.toString());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Welcome to the Advanced Password Strength Checker!");

        while (true) {
            System.out.print("\nEnter a password (or type 'exit' to quit): ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String result = checkStrength(input);
            System.out.println("\nResult:\n" + result);
        }

        sc.close();
        System.out.println("Goodbye! Stay secure. ");
    }
}

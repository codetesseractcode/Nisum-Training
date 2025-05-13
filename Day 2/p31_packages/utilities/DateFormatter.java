package p31_packages.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DateFormatter utility class
 */
public class DateFormatter {
    /**
     * Format current date as dd-MM-yyyy
     */
    public String formatCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return today.format(formatter);
    }
    
    /**
     * Format given date as dd-MM-yyyy
     */
    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
    
    /**
     * Format date with custom pattern
     */
    public String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
}

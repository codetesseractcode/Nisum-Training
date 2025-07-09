package p31_packages.utilities;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * CurrencyFormatter utility class with static methods
 */
public class CurrencyFormatter {
    /**
     * Format amount as Indian currency (₹)
     */
    public static String formatCurrency(double amount) {
        Locale indianLocale = new Locale("en", "IN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(indianLocale);
        return currencyFormatter.format(amount);
    }
    
    /**
     * Format amount with custom locale
     */
    public static String formatCurrency(double amount, String countryCode) {
        Locale locale = new Locale("en", countryCode);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount);
    }
}

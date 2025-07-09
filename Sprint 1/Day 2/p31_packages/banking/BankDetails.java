package p31_packages.banking;

/**
 * BankDetails class with static members in the banking package
 */
public class BankDetails {
    // Public static constant accessible from outside the package
    public static final String BANK_NAME = "Indian National Bank";
    
    // Private static constant only accessible within this class
    private static final String BANK_CODE = "INB001";
    
    // Static method
    public static String getBranchCode(String city) {
        if ("Mumbai".equalsIgnoreCase(city)) {
            return "MUM";
        } else if ("Delhi".equalsIgnoreCase(city)) {
            return "DEL";
        } else if ("Chennai".equalsIgnoreCase(city)) {
            return "CHE";
        } else if ("Bangalore".equalsIgnoreCase(city)) {
            return "BAN";
        } else {
            return "OTH";
        }
    }
}

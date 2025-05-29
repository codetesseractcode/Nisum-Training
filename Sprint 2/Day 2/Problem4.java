// Problem 4: Given a 4-digit storeId string, pad with leading zeros if the length is less than 4.
public class Problem4 {
    public static void main(String[] args) {
        String storeId = "123";
        
        String paddedStoreId = String.format("%04d", Integer.parseInt(storeId));
                
        System.out.println("Original storeId: " + storeId);
        System.out.println("Padded storeId: " + paddedStoreId);
        
        // Additional examples
        System.out.println("Padding '7': " + String.format("%04d", Integer.parseInt("7")));
        System.out.println("Padding '45': " + String.format("%04d", Integer.parseInt("45")));
        System.out.println("Padding '1234': " + String.format("%04d", Integer.parseInt("1234")));
    }
}

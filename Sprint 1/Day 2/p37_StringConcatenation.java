/**
 * Program to concatenate a given string to the end of another string
 * 
 * This program demonstrates various ways to concatenate strings in Java:
 * 1. Using + operator
 * 2. Using concat() method
 * 3. Using StringBuilder
 * 4. Using StringBuffer
 * 5. Using String.join() method
 */

public class p37_StringConcatenation {
    public static void main(String[] args) {
        System.out.println("=== String Concatenation Demonstration ===\n");
        
        // Example 1: Using + operator for string concatenation
        System.out.println("1. Concatenation using + operator:");
        String firstName = "Rahul";
        String lastName = "Sharma";
        
        String fullName = firstName + " " + lastName;
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Full name: " + fullName);
        
        // More complex concatenation with + operator
        String address = "123 " + "Main Street, " + "Mumbai, " + "Maharashtra " + "400001";
        System.out.println("Address: " + address);
        System.out.println();
        
        // Example 2: Using concat() method
        System.out.println("2. Concatenation using concat() method:");
        String greeting = "Hello";
        String name = "Priya";
        
        String message = greeting.concat(" ").concat(name).concat("!");
        System.out.println("Greeting: " + greeting);
        System.out.println("Name: " + name);
        System.out.println("Message: " + message);
        
        // Chained concat
        String university = "Delhi".concat(" ").concat("University");
        System.out.println("University: " + university);
        System.out.println();
        
        // Example 3: Using StringBuilder (mutable, not thread-safe)
        System.out.println("3. Concatenation using StringBuilder (mutable, not thread-safe):");
        StringBuilder emailBuilder = new StringBuilder();
        
        String username = "rajesh.kumar";
        String domain = "gmail.com";
        
        emailBuilder.append(username);
        emailBuilder.append("@");
        emailBuilder.append(domain);
        
        String email = emailBuilder.toString();
        System.out.println("Username: " + username);
        System.out.println("Domain: " + domain);
        System.out.println("Email: " + email);
        
        // StringBuilder with initial capacity and chaining
        StringBuilder addressBuilder = new StringBuilder(100)
            .append("456 ")
            .append("Park Avenue, ")
            .append("Chennai, ")
            .append("Tamil Nadu ")
            .append("600001");
        
        System.out.println("Address built with StringBuilder: " + addressBuilder.toString());
        System.out.println();
        
        // Example 4: Using StringBuffer (mutable, thread-safe)
        System.out.println("4. Concatenation using StringBuffer (mutable, thread-safe):");
        StringBuffer reportBuffer = new StringBuffer();
        
        reportBuffer.append("Sales Report\n");
        reportBuffer.append("Date: 13-May-2023\n");
        reportBuffer.append("Products Sold: 156\n");
        reportBuffer.append("Total Revenue: ₹45,780");
        
        String report = reportBuffer.toString();
        System.out.println("Report built with StringBuffer:");
        System.out.println(report);
        System.out.println();
        
        // Example 5: Using String.join() method (Java 8+)
        System.out.println("5. Concatenation using String.join() method:");
        
        // Join array elements
        String[] fruits = {"Apple", "Banana", "Orange", "Mango"};
        String fruitList = String.join(", ", fruits);
        System.out.println("Fruits: " + fruitList);
        
        // Join individual strings
        String csvHeader = String.join(",", "Name", "Age", "City", "Occupation");
        System.out.println("CSV Header: " + csvHeader);
        
        // Join with a custom delimiter
        String timeString = String.join(":", "10", "30", "45");
        System.out.println("Time: " + timeString);
        System.out.println();
        
        // Example 6: Performance comparison
        System.out.println("6. Performance comparison for multiple concatenations:");
        performanceDemonstration();
        
        System.out.println("\n=== End of String Concatenation Demonstration ===");
    }
    
    // Method to demonstrate performance differences between concatenation methods
    private static void performanceDemonstration() {
        int iterations = 10000;
        
        // Using + operator (String concatenation)
        long startTime = System.currentTimeMillis();
        String result1 = "";
        for (int i = 0; i < iterations; i++) {
            result1 = result1 + i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with + operator: " + (endTime - startTime) + " ms");
        System.out.println("Final string length: " + result1.length());
        
        // Using StringBuilder
        startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }
        String result2 = sb.toString();
        endTime = System.currentTimeMillis();
        System.out.println("Time taken with StringBuilder: " + (endTime - startTime) + " ms");
        System.out.println("Final string length: " + result2.length());
        
        // Using StringBuffer (thread-safe, generally slower than StringBuilder)
        startTime = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            buffer.append(i);
        }
        String result3 = buffer.toString();
        endTime = System.currentTimeMillis();
        System.out.println("Time taken with StringBuffer: " + (endTime - startTime) + " ms");
        System.out.println("Final string length: " + result3.length());
    }
}

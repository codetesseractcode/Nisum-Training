/*
 4. Write a program to create multiple 'Cart' objects which has variables like itemName,
    itemValue and itemId. Validate the values of these variables and build an order 
    summary with itemsCount and orderTotal.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class p4_ShoppingCart 
{
    public static void main(String[] args) 
    {
        // Create a cart manager to handle cart operations
        CartManager cartManager = new CartManager();
        
        // Create multiple cart items through user input
        List<CartItem> cartItems = cartManager.createCartItems();
        
        // Display order summary
        cartManager.displayOrderSummary(cartItems);
    }
}

/**
 * Class representing a cart item
 */
class CartItem 
{
    private String itemId;
    private String itemName;
    private double itemValue;
    
    // Constructor
    public CartItem(String itemId, String itemName, double itemValue) 
    {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }
    
    // Getters
    public String getItemId() 
    {
        return itemId;
    }
    
    public String getItemName() 
    {
        return itemName;
    }
    
    public double getItemValue() 
    {
        return itemValue;
    }
    
    @Override
    public String toString() 
    {
        return "CartItem [ID=" + itemId + ", Name=" + itemName + ", Value=$" + String.format("%.2f", itemValue) + "]";
    }
}

/**
 * Manager class to handle operations related to cart items
 */
class CartManager 
{
    private Scanner scanner;
    
    public CartManager() 
    {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Create multiple cart items with validation
     * @return List of valid cart items
     */
    public List<CartItem> createCartItems() 
    {
        List<CartItem> items = new ArrayList<>();
        
        System.out.println("=== Shopping Cart Creation ===");
        
        boolean addMore = true;
        while (addMore) {
            // Get item details with validation
            CartItem item = createSingleCartItem();
            items.add(item);
            
            // Ask if user wants to add more items
            System.out.print("\nAdd another item? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            addMore = response.equals("yes") || response.equals("y");
        }
        
        return items;
    }
    
    /**
     * Create a single cart item with validation
     * @return A valid cart item
     */
    private CartItem createSingleCartItem() 
    {
        System.out.println("\n--- Add New Item ---");
        
        // Validate Item ID
        String itemId;
        do {
            System.out.print("Enter Item ID: ");
            itemId = scanner.nextLine().trim();
            if (itemId.isEmpty()) 
            {
                System.out.println("Item ID cannot be empty. Please try again.");
            } 
            else if (!isValidItemId(itemId)) 
            {
                System.out.println("Invalid Item ID. Must start with 'ID-' followed by numbers.");
                itemId = "";
            }
        } 
        while (itemId.isEmpty());
        
        // Validate Item Name
        String itemName;
        do 
        {
            System.out.print("Enter Item Name: ");
            itemName = scanner.nextLine().trim();
            if (itemName.isEmpty()) 
            {
                System.out.println("Item Name cannot be empty. Please try again.");
            }
        } 
        while (itemName.isEmpty());
        
        // Validate Item Value
        double itemValue;
        do 
        {
            System.out.print("Enter Item Value ($): ");
            itemValue = getValidDoubleInput();
            scanner.nextLine(); // Clear buffer
            
            if (itemValue <= 0) 
            {
                System.out.println("Item Value must be greater than 0. Please try again.");
            }
        } 
        while (itemValue <= 0);
        
        return new CartItem(itemId, itemName, itemValue);
    }
    
    /**
     * Display the order summary
     * @param items List of cart items
     */
    public void displayOrderSummary(List<CartItem> items) 
    {
        System.out.println("\n=== Order Summary ===");
        
        int itemsCount = items.size();
        double orderTotal = 0.0;
        
        System.out.println("Items in Cart:");
        for (int i = 0; i < items.size(); i++) 
        {
            CartItem item = items.get(i);
            System.out.println((i+1) + ". " + item);
            orderTotal += item.getItemValue();
        }
        
        System.out.println("\nItems Count: " + itemsCount);
        System.out.println("Order Total: $" + String.format("%.2f", orderTotal));
    }
    
    /**
     * Helper method to validate item ID format
     * @param itemId The itemId to validate
     * @return true if the itemId is valid, false otherwise
     */
    private boolean isValidItemId(String itemId) 
    {
        // Item ID should start with 'ID-' followed by numbers
        String itemIdRegex = "^ID-\\d+$";
        return itemId.matches(itemIdRegex);
    }
    
    /**
     * Helper method to validate double input
     * @return A valid double value
     */
    private double getValidDoubleInput() 
    {
        while (!scanner.hasNextDouble()) 
        {
            System.out.println("Invalid input. Please enter a numeric value.");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}

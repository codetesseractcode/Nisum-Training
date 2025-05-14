import java.util.HashMap;

public class q4_ProductInventory {
    private HashMap<String, Integer> products;

    public q4_ProductInventory() {
        products = new HashMap<>();
    }

    public void addProduct(String name, int quantity) {
        if (products.containsKey(name)) {
            int currentQuantity = products.get(name);
            products.put(name, currentQuantity + quantity);
        } else {
            products.put(name, quantity);
        }
    }

    public void updateQuantity(String name, int newQuantity) {
        if (products.containsKey(name)) {
            products.put(name, newQuantity);
        } else {
            System.out.println("Product not found: " + name);
        }
    }

    public void removeProduct(String name) {
        if (products.containsKey(name)) {
            products.remove(name);
            System.out.println("Product removed: " + name);
        } else {
            System.out.println("Product not found: " + name);
        }
    }

    public boolean isInStock(String name) {
        if (products.containsKey(name)) {
            return products.get(name) > 0;
        }
        return false;
    }

    public int getQuantity(String name) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return 0;
    }

    public void displayInventory() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }

        System.out.println("Product Inventory:");
        System.out.println("---------------------------");
        System.out.printf("%-20s %s%n", "Product", "Quantity");
        System.out.println("---------------------------");
        
        for (String name : products.keySet()) {
            System.out.printf("%-20s %d%n", name, products.get(name));
        }
        System.out.println("---------------------------");
    }    public static void main(String[] args) {
        q4_ProductInventory inventory = new q4_ProductInventory();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Product Inventory System =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add product");
            System.out.println("2. Update product quantity");
            System.out.println("3. Remove product");
            System.out.println("4. Check if product is in stock");
            System.out.println("5. Display inventory");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            
            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
            } catch (Exception e) {
                scanner.nextLine(); // Clear the buffer
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    
                    System.out.print("Enter quantity: ");
                    int quantity = 0;
                    try {
                        quantity = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid quantity. Product not added.");
                        continue;
                    }
                    
                    inventory.addProduct(productName, quantity);
                    System.out.println("Product added to inventory.");
                    break;
                    
                case 2:
                    System.out.print("Enter product name: ");
                    String updateProductName = scanner.nextLine();
                    
                    System.out.print("Enter new quantity: ");
                    int newQuantity = 0;
                    try {
                        newQuantity = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid quantity. Update failed.");
                        continue;
                    }
                    
                    inventory.updateQuantity(updateProductName, newQuantity);
                    break;
                    
                case 3:
                    System.out.print("Enter product name to remove: ");
                    String removeProductName = scanner.nextLine();
                    inventory.removeProduct(removeProductName);
                    break;
                    
                case 4:
                    System.out.print("Enter product name to check: ");
                    String checkProductName = scanner.nextLine();
                    if (inventory.isInStock(checkProductName)) {
                        System.out.println(checkProductName + " is in stock. Quantity: " + inventory.getQuantity(checkProductName));
                    } else {
                        System.out.println(checkProductName + " is not in stock or doesn't exist.");
                    }
                    break;
                    
                case 5:
                    inventory.displayInventory();
                    break;
                    
                case 6:
                    running = false;
                    System.out.println("Exiting Product Inventory System.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

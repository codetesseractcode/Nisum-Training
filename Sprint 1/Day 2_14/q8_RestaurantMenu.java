import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

class MenuItem {
    private String name;
    private String description;
    private double price;
    
    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return String.format("%-20s $%.2f\n   %s", name, price, description);
    }
}

public class q8_RestaurantMenu {
    private LinkedHashMap<String, HashMap<String, MenuItem>> menu;
    
    public q8_RestaurantMenu() {
        menu = new LinkedHashMap<>();
    }
    
    public void addCategory(String category) {
        if (!menu.containsKey(category)) {
            menu.put(category, new HashMap<>());
        }
    }
    
    public void addMenuItem(String category, String name, String description, double price) {
        if (!menu.containsKey(category)) {
            addCategory(category);
        }
        
        MenuItem item = new MenuItem(name, description, price);
        menu.get(category).put(name, item);
    }
    
    public void removeMenuItem(String category, String name) {
        if (menu.containsKey(category) && menu.get(category).containsKey(name)) {
            menu.get(category).remove(name);
            System.out.println("Removed " + name + " from " + category);
        } else {
            System.out.println("Item not found: " + name + " in " + category);
        }
    }
    
    public void updatePrice(String category, String name, double newPrice) {
        if (menu.containsKey(category) && menu.get(category).containsKey(name)) {
            menu.get(category).get(name).setPrice(newPrice);
            System.out.println("Updated price for " + name);
        } else {
            System.out.println("Item not found: " + name + " in " + category);
        }
    }
    
    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("Menu is empty");
            return;
        }
        
        System.out.println("\n========== RESTAURANT MENU ==========\n");
        
        for (Map.Entry<String, HashMap<String, MenuItem>> categoryEntry : menu.entrySet()) {
            String category = categoryEntry.getKey();
            HashMap<String, MenuItem> items = categoryEntry.getValue();
            
            System.out.println("=== " + category.toUpperCase() + " ===");
            
            if (items.isEmpty()) {
                System.out.println("No items in this category");
            } else {
                for (MenuItem item : items.values()) {
                    System.out.println(item);
                }
            }
            System.out.println();
        }
        System.out.println("===================================");
    }
    public static void main(String[] args) {
        q8_RestaurantMenu menu = new q8_RestaurantMenu();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Restaurant Menu System =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new category");
            System.out.println("2. Add a new menu item");
            System.out.println("3. Update price of an item");
            System.out.println("4. Remove a menu item");
            System.out.println("5. Display menu");
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
                    System.out.print("Enter new category name: ");
                    String category = scanner.nextLine();
                    menu.addCategory(category);
                    System.out.println("Category added: " + category);
                    break;
                    
                case 2:
                    System.out.print("Enter category: ");
                    String itemCategory = scanner.nextLine();
                    
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter item description: ");
                    String description = scanner.nextLine();
                    
                    System.out.print("Enter price: $");
                    double price = 0.0;
                    try {
                        price = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid price. Item not added.");
                        continue;
                    }
                    
                    menu.addMenuItem(itemCategory, name, description, price);
                    System.out.println("Menu item added successfully.");
                    break;
                    
                case 3:
                    System.out.print("Enter category: ");
                    String updateCategory = scanner.nextLine();
                    
                    System.out.print("Enter item name: ");
                    String updateItem = scanner.nextLine();
                    
                    System.out.print("Enter new price: $");
                    double newPrice = 0.0;
                    try {
                        newPrice = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid price. Update failed.");
                        continue;
                    }
                    
                    menu.updatePrice(updateCategory, updateItem, newPrice);
                    break;
                    
                case 4:
                    System.out.print("Enter category: ");
                    String removeCategory = scanner.nextLine();
                    
                    System.out.print("Enter item name to remove: ");
                    String removeItem = scanner.nextLine();
                    
                    menu.removeMenuItem(removeCategory, removeItem);
                    break;
                    
                case 5:
                    menu.displayMenu();
                    break;
                    
                case 6:
                    running = false;
                    System.out.println("Exiting Restaurant Menu System.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

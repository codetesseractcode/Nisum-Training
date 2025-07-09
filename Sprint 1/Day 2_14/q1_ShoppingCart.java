import java.util.ArrayList;

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " x " + quantity + " = $" + getTotalPrice();
    }
}

public class q1_ShoppingCart {
    private ArrayList<Item> items;

    public q1_ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String name, double price, int quantity) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new Item(name, price, quantity));
    }

    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equals(name));
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        
        System.out.println("Shopping Cart:");
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }    public static void main(String[] args) {
        q1_ShoppingCart cart = new q1_ShoppingCart();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Shopping Cart Application =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add item to cart");
            System.out.println("2. Remove item from cart");
            System.out.println("3. View cart");
            System.out.println("4. Calculate total");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            
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
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter item price: $");
                    double price = 0;
                    try {
                        price = scanner.nextDouble();
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid price. Item not added.");
                        continue;
                    }
                    
                    System.out.print("Enter quantity: ");
                    int quantity = 0;
                    try {
                        quantity = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid quantity. Item not added.");
                        continue;
                    }
                    
                    cart.addItem(name, price, quantity);
                    System.out.println("Item added to cart.");
                    break;
                    
                case 2:
                    System.out.print("Enter item name to remove: ");
                    String itemToRemove = scanner.nextLine();
                    cart.removeItem(itemToRemove);
                    System.out.println("Item removed from cart (if it existed).");
                    break;
                    
                case 3:
                    cart.viewCart();
                    break;
                    
                case 4:
                    System.out.printf("Total: $%.2f\n", cart.calculateTotal());
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("Thank you for shopping with us!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

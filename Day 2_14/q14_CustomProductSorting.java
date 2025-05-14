import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Product {
    private String name;
    private String category;
    private double price;
    
    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return String.format("%-15s %-12s $%.2f", name, category, price);
    }
}

public class q14_CustomProductSorting {    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Custom Product Sorting =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a product");
            System.out.println("2. Display all products (unsorted)");
            System.out.println("3. Sort products by category, then price");
            System.out.println("4. Sort products by price only");
            System.out.println("5. Sort products by name (alphabetically)");
            System.out.println("6. Use sample product list");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");
            
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
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter product category: ");
                    String category = scanner.nextLine();
                    
                    System.out.print("Enter product price: $");
                    double price = 0.0;
                    try {
                        price = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid price. Product not added.");
                        continue;
                    }
                    
                    products.add(new Product(name, category, price));
                    System.out.println("Product added successfully.");
                    break;
                    
                case 2:
                    if (products.isEmpty()) {
                        System.out.println("No products in the list yet.");
                    } else {
                        displayProducts(products, "Product List (Unsorted)");
                    }
                    break;
                    
                case 3:
                    if (products.isEmpty()) {
                        System.out.println("No products in the list yet.");
                    } else {
                        // Sort by category, then price
                        Collections.sort(products, new Comparator<Product>() {
                            @Override
                            public int compare(Product p1, Product p2) {
                                int categoryComparison = p1.getCategory().compareTo(p2.getCategory());
                                
                                if (categoryComparison == 0) {
                                    return Double.compare(p1.getPrice(), p2.getPrice());
                                }
                                
                                return categoryComparison;
                            }
                        });
                        
                        displayProductsByCategory(products, "Products Sorted by Category, then Price");
                    }
                    break;
                    
                case 4:
                    if (products.isEmpty()) {
                        System.out.println("No products in the list yet.");
                    } else {
                        // Sort by price only
                        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
                        displayProducts(products, "Products Sorted by Price (Low-High)");
                    }
                    break;
                    
                case 5:
                    if (products.isEmpty()) {
                        System.out.println("No products in the list yet.");
                    } else {
                        // Sort by name alphabetically
                        Collections.sort(products, Comparator.comparing(Product::getName));
                        displayProducts(products, "Products Sorted by Name (A-Z)");
                    }
                    break;
                    
                case 6:
                    // Use sample data
                    products.clear();
                    products.add(new Product("Laptop", "Electronics", 899.99));
                    products.add(new Product("Smartphone", "Electronics", 499.99));
                    products.add(new Product("Banana", "Grocery", 1.99));
                    products.add(new Product("Apple", "Grocery", 0.99));
                    products.add(new Product("T-Shirt", "Clothing", 19.99));
                    products.add(new Product("Jeans", "Clothing", 49.99));
                    products.add(new Product("Desk", "Furniture", 199.99));
                    products.add(new Product("Chair", "Furniture", 89.99));
                    System.out.println("Sample product list loaded.");
                    displayProducts(products, "Sample Product List");
                    break;
                    
                case 7:
                    running = false;
                    System.out.println("Exiting Custom Product Sorting.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayProducts(List<Product> products, String title) {
        System.out.println("\n" + title + ":");
        System.out.println("--------------------------------------------");
        System.out.printf("%-15s %-12s %s%n", "Name", "Category", "Price");
        System.out.println("--------------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }
    
    private static void displayProductsByCategory(List<Product> products, String title) {
        System.out.println("\n" + title + ":");
        System.out.println("--------------------------------------------");
        System.out.printf("%-15s %-12s %s%n", "Name", "Category", "Price");
        System.out.println("--------------------------------------------");
        
        String currentCategory = "";
        for (Product product : products) {
            if (!currentCategory.equals(product.getCategory())) {
                currentCategory = product.getCategory();
                System.out.println("-- " + currentCategory + " --");
            }
            System.out.println(product);
        }
    }
}

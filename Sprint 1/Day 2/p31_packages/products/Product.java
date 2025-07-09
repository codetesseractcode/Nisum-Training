package p31_packages.products;

/**
 * Product class in the products package
 */
public class Product {
    private String productId;
    private String name;
    private double price;
    
    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void displayInfo() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " INR");
    }
    
    public double calculateDiscountedPrice(double discountPercentage) {
        return price - (price * discountPercentage / 100);
    }
}

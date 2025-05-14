import java.util.ArrayDeque;

public class q11_SimpleStack<E> {
    private ArrayDeque<E> stack;
    
    public q11_SimpleStack() {
        stack = new ArrayDeque<>();
    }
    
    public void push(E element) {
        stack.push(element);
    }
    
    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.pop();
    }
    
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.peek();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public int size() {
        return stack.size();
    }
    public static void main(String[] args) {
        q11_SimpleStack<String> stack = new q11_SimpleStack<>();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Simple Stack Implementation =====");
        System.out.println("This program implements a stack of strings using ArrayDeque");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Push an element onto the stack");
            System.out.println("2. Pop an element from the stack");
            System.out.println("3. Peek at the top element");
            System.out.println("4. Check if stack is empty");
            System.out.println("5. Get stack size");
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
                    System.out.print("Enter element to push: ");
                    String element = scanner.nextLine();
                    stack.push(element);
                    System.out.println("Pushed: " + element);
                    break;
                    
                case 2:
                    try {
                        String popped = stack.pop();
                        System.out.println("Popped: " + popped);
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case 3:
                    try {
                        String top = stack.peek();
                        System.out.println("Top element: " + top);
                    } catch (IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case 4:
                    System.out.println("Is stack empty? " + stack.isEmpty());
                    break;
                    
                case 5:
                    System.out.println("Stack size: " + stack.size());
                    break;
                    
                case 6:
                    running = false;
                    System.out.println("Exiting Simple Stack Implementation.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

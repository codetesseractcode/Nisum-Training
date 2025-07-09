import java.util.Stack;

public class q5_BrowserHistory {
    private Stack<String> history;
    private String currentPage;

    public q5_BrowserHistory() {
        history = new Stack<>();
        currentPage = null;
    }

    public void visitPage(String url) {
        if (currentPage != null) {
            history.push(currentPage);
        }
        currentPage = url;
        System.out.println("Visiting: " + url);
    }

    public void goBack() {
        if (history.isEmpty()) {
            System.out.println("No previous pages in history.");
            return;
        }
        
        currentPage = history.pop();
        System.out.println("Going back to: " + currentPage);
    }

    public void viewCurrentPage() {
        if (currentPage == null) {
            System.out.println("No page currently being viewed.");
        } else {
            System.out.println("Current page: " + currentPage);
        }
    }

    public void viewHistory() {
        if (history.isEmpty() && currentPage == null) {
            System.out.println("Browser history is empty.");
            return;
        }

        System.out.println("\nBrowser History (oldest to newest):");
        
        Object[] historyArray = history.toArray();
        for (int i = 0; i < historyArray.length; i++) {
            System.out.println((i + 1) + ". " + historyArray[i]);
        }
        
        if (currentPage != null) {
            System.out.println("Current: " + currentPage + " (current page)");
        }
    }    public static void main(String[] args) {
        q5_BrowserHistory browser = new q5_BrowserHistory();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Browser History Simulator =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Visit a new page");
            System.out.println("2. Go back to previous page");
            System.out.println("3. View current page");
            System.out.println("4. View browser history");
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
                    System.out.print("Enter URL to visit: ");
                    String url = scanner.nextLine();
                    browser.visitPage(url);
                    break;
                    
                case 2:
                    browser.goBack();
                    break;
                    
                case 3:
                    browser.viewCurrentPage();
                    break;
                    
                case 4:
                    browser.viewHistory();
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("Closing browser.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

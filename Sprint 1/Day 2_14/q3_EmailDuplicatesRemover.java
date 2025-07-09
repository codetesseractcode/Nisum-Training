import java.util.HashSet;

public class q3_EmailDuplicatesRemover {
    private HashSet<String> uniqueEmails;

    public q3_EmailDuplicatesRemover() {
        uniqueEmails = new HashSet<>();
    }

    public void addEmail(String email) {
        uniqueEmails.add(email.trim().toLowerCase());
    }

    public void addMultipleEmails(String[] emails) {
        for (String email : emails) {
            addEmail(email);
        }
    }

    public void displayUniqueEmails() {
        if (uniqueEmails.isEmpty()) {
            System.out.println("No email addresses stored.");
            return;
        }

        System.out.println("Unique Email Addresses (" + uniqueEmails.size() + "):");
        for (String email : uniqueEmails) {
            System.out.println(email);
        }
    }

    public int getEmailCount() {
        return uniqueEmails.size();
    }    public static void main(String[] args) {
        q3_EmailDuplicatesRemover remover = new q3_EmailDuplicatesRemover();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Email Duplicates Remover =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a single email");
            System.out.println("2. Add multiple emails (comma separated)");
            System.out.println("3. Display unique emails");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
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
                    System.out.print("Enter email address: ");
                    String email = scanner.nextLine();
                    remover.addEmail(email);
                    System.out.println("Email added.");
                    break;
                    
                case 2:
                    System.out.print("Enter multiple email addresses (comma separated): ");
                    String emailsInput = scanner.nextLine();
                    String[] emails = emailsInput.split(",");
                    remover.addMultipleEmails(emails);
                    System.out.println(emails.length + " emails processed.");
                    break;
                    
                case 3:
                    remover.displayUniqueEmails();
                    System.out.println("Total unique emails: " + remover.getEmailCount());
                    break;
                    
                case 4:
                    running = false;
                    System.out.println("Thank you for using Email Duplicates Remover!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

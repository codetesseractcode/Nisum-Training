import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

class Event {
    private String title;
    private String time;
    private String description;
    
    public Event(String title, String time, String description) {
        this.title = title;
        this.time = time;
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getTime() {
        return time;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return time + " - " + title + ": " + description;
    }
}

public class q9_EventCalendar {
    private TreeMap<String, ArrayList<Event>> calendar;
    
    public q9_EventCalendar() {
        calendar = new TreeMap<>();
    }
    
    public void addEvent(String date, String title, String time, String description) {
        if (!calendar.containsKey(date)) {
            calendar.put(date, new ArrayList<>());
        }
        
        Event event = new Event(title, time, description);
        calendar.get(date).add(event);
        System.out.println("Added: " + title + " on " + date);
    }
    
    public void removeEvent(String date, String title) {
        if (calendar.containsKey(date)) {
            ArrayList<Event> events = calendar.get(date);
            boolean removed = events.removeIf(event -> event.getTitle().equals(title));
            
            if (removed) {
                System.out.println("Removed: " + title + " from " + date);
                if (events.isEmpty()) {
                    calendar.remove(date);
                }
            } else {
                System.out.println("Event not found: " + title + " on " + date);
            }
        } else {
            System.out.println("No events found for date: " + date);
        }
    }
    
    public void displayEventsForDate(String date) {
        if (calendar.containsKey(date) && !calendar.get(date).isEmpty()) {
            ArrayList<Event> events = calendar.get(date);
            System.out.println("\nEvents for " + date + ":");
            System.out.println("----------------------------");
            for (Event event : events) {
                System.out.println(event);
            }
            System.out.println("----------------------------");
        } else {
            System.out.println("\nNo events scheduled for " + date);
        }
    }
    
    public void listAllUpcomingEvents(String fromDate) {
        Map<String, ArrayList<Event>> upcomingEvents = calendar.tailMap(fromDate);
        
        if (upcomingEvents.isEmpty()) {
            System.out.println("\nNo upcoming events from " + fromDate);
            return;
        }
        
        System.out.println("\nAll upcoming events from " + fromDate + ":");
        System.out.println("===================================");
        
        for (Map.Entry<String, ArrayList<Event>> entry : upcomingEvents.entrySet()) {
            String date = entry.getKey();
            ArrayList<Event> events = entry.getValue();
            
            System.out.println("\n" + date + ":");
            System.out.println("----------------------------");
            for (Event event : events) {
                System.out.println(event);
            }
        }
        System.out.println("===================================");
    }
    public static void main(String[] args) {
        q9_EventCalendar calendar = new q9_EventCalendar();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Event Calendar Application =====");
        System.out.println("Today's date is: " + java.time.LocalDate.now());
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new event");
            System.out.println("2. Remove an event");
            System.out.println("3. Display events for a specific date");
            System.out.println("4. List all upcoming events from a date");
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
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    
                    System.out.print("Enter event title: ");
                    String title = scanner.nextLine();
                    
                    System.out.print("Enter time (HH:MM): ");
                    String time = scanner.nextLine();
                    
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    
                    calendar.addEvent(date, title, time, description);
                    break;
                    
                case 2:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String removeDate = scanner.nextLine();
                    
                    System.out.print("Enter event title to remove: ");
                    String removeTitle = scanner.nextLine();
                    
                    calendar.removeEvent(removeDate, removeTitle);
                    break;
                    
                case 3:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String displayDate = scanner.nextLine();
                    
                    calendar.displayEventsForDate(displayDate);
                    break;
                    
                case 4:
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    
                    calendar.listAllUpcomingEvents(startDate);
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("Exiting Event Calendar Application.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

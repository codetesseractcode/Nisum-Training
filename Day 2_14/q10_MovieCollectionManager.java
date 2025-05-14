import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Movie {
    private String title;
    private String director;
    private String genre;
    private int releaseYear;
    private double rating;
    
    public Movie(String title, String director, String genre, int releaseYear, double rating) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDirector() {
        return director;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public int getReleaseYear() {
        return releaseYear;
    }
    
    public double getRating() {
        return rating;
    }
}

public class q10_MovieCollectionManager {
    private ArrayList<Movie> movies;
    
    public q10_MovieCollectionManager() {
        movies = new ArrayList<>();
    }
    
    public void addMovie(String title, String director, String genre, int releaseYear, double rating) {
        movies.add(new Movie(title, director, genre, releaseYear, rating));
    }
    
    public void removeMovie(String title) {
        boolean removed = movies.removeIf(movie -> movie.getTitle().equals(title));
        if (removed) {
            System.out.println("Removed: " + title);
        } else {
            System.out.println("Movie not found: " + title);
        }
    }
    
    public List<Movie> filterByGenre(String genre) {
        return movies.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
    
    public List<Movie> filterByDirector(String director) {
        return movies.stream()
                .filter(movie -> movie.getDirector().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }
    
    public List<Movie> filterByReleaseYear(int year) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() == year)
                .collect(Collectors.toList());
    }
    
    public List<Movie> sortByTitle() {
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(Comparator.comparing(Movie::getTitle));
        return sorted;
    }
    
    public List<Movie> sortByReleaseYear() {
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(Comparator.comparingInt(Movie::getReleaseYear));
        return sorted;
    }
    
    public List<Movie> sortByRating() {
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        return sorted;
    }
    
    public void displayMovies(List<Movie> movieList) {
        if (movieList.isEmpty()) {
            System.out.println("No movies to display.");
            return;
        }
        
        System.out.println("+-----------------------+--------------------+------------+------+--------+");
        System.out.println("| Title                 | Director           | Genre      | Year | Rating |");
        System.out.println("+-----------------------+--------------------+------------+------+--------+");
        
        for (Movie movie : movieList) {
            System.out.printf("| %-21s | %-18s | %-10s | %4d | %6.1f |\n",
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getGenre(),
                    movie.getReleaseYear(),
                    movie.getRating());
        }
        System.out.println("+-----------------------+--------------------+------------+------+--------+");
    }
    public static void main(String[] args) {
        q10_MovieCollectionManager collection = new q10_MovieCollectionManager();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Movie Collection Manager =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a movie");
            System.out.println("2. Remove a movie");
            System.out.println("3. Display all movies");
            System.out.println("4. Filter movies by genre");
            System.out.println("5. Filter movies by director");
            System.out.println("6. Filter movies by release year");
            System.out.println("7. Sort movies by title");
            System.out.println("8. Sort movies by release year");
            System.out.println("9. Sort movies by rating (highest first)");
            System.out.println("10. Exit");
            System.out.print("Enter your choice (1-10): ");
            
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
                    System.out.print("Enter movie title: ");
                    String title = scanner.nextLine();
                    
                    System.out.print("Enter director: ");
                    String director = scanner.nextLine();
                    
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    
                    System.out.print("Enter release year: ");
                    int year = 0;
                    try {
                        year = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid year. Movie not added.");
                        continue;
                    }
                    
                    System.out.print("Enter rating (0.0-10.0): ");
                    double rating = 0.0;
                    try {
                        rating = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                        if (rating < 0.0 || rating > 10.0) {
                            System.out.println("Rating must be between 0.0 and 10.0. Movie not added.");
                            continue;
                        }
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid rating. Movie not added.");
                        continue;
                    }
                    
                    collection.addMovie(title, director, genre, year, rating);
                    System.out.println("Movie added successfully.");
                    break;
                    
                case 2:
                    System.out.print("Enter movie title to remove: ");
                    String removeTitle = scanner.nextLine();
                    collection.removeMovie(removeTitle);
                    break;
                    
                case 3:
                    System.out.println("\nAll Movies:");
                    collection.displayMovies(collection.sortByTitle());
                    break;
                    
                case 4:
                    System.out.print("Enter genre to filter by: ");
                    String filterGenre = scanner.nextLine();
                    System.out.println("\nMovies in genre: " + filterGenre);
                    collection.displayMovies(collection.filterByGenre(filterGenre));
                    break;
                    
                case 5:
                    System.out.print("Enter director to filter by: ");
                    String filterDirector = scanner.nextLine();
                    System.out.println("\nMovies directed by: " + filterDirector);
                    collection.displayMovies(collection.filterByDirector(filterDirector));
                    break;
                    
                case 6:
                    System.out.print("Enter release year to filter by: ");
                    int filterYear = 0;
                    try {
                        filterYear = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid year.");
                        continue;
                    }
                    System.out.println("\nMovies released in: " + filterYear);
                    collection.displayMovies(collection.filterByReleaseYear(filterYear));
                    break;
                    
                case 7:
                    System.out.println("\nMovies sorted by title:");
                    collection.displayMovies(collection.sortByTitle());
                    break;
                    
                case 8:
                    System.out.println("\nMovies sorted by release year:");
                    collection.displayMovies(collection.sortByReleaseYear());
                    break;
                    
                case 9:
                    System.out.println("\nMovies sorted by rating (highest first):");
                    collection.displayMovies(collection.sortByRating());
                    break;
                    
                case 10:
                    running = false;
                    System.out.println("Exiting Movie Collection Manager.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

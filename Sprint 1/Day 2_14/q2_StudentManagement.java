import java.util.ArrayList;

class Student {
    private int id;
    private String name;
    private char grade;

    public Student(int id, String name, char grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}

public class q2_StudentManagement {
    private ArrayList<Student> students;

    public q2_StudentManagement() {
        students = new ArrayList<>();
    }

    public void addStudent(int id, String name, char grade) {
        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("Student with ID " + id + " already exists.");
                return;
            }
        }
        students.add(new Student(id, name, grade));
    }

    public void removeStudent(int id) {
        boolean removed = students.removeIf(student -> student.getId() == id);
        if (!removed) {
            System.out.println("Student with ID " + id + " not found.");
        } else {
            System.out.println("Student with ID " + id + " removed successfully.");
        }
    }

    public Student searchStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        System.out.println("Student List:");
        for (Student student : students) {
            System.out.println(student);
        }
    }    public static void main(String[] args) {
        q2_StudentManagement management = new q2_StudentManagement();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Student Management System =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new student");
            System.out.println("2. Remove a student");
            System.out.println("3. Search for a student by ID");
            System.out.println("4. Display all students");
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
                    System.out.print("Enter student ID: ");
                    int id = 0;
                    try {
                        id = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid ID. Student not added.");
                        continue;
                    }
                    
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter student grade (A-F): ");
                    char grade = ' ';
                    try {
                        String gradeInput = scanner.nextLine().toUpperCase();
                        if (!gradeInput.isEmpty()) {
                            grade = gradeInput.charAt(0);
                            if (grade < 'A' || grade > 'F') {
                                System.out.println("Invalid grade. Must be A-F. Student not added.");
                                continue;
                            }
                        } else {
                            System.out.println("Grade cannot be empty. Student not added.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid grade. Student not added.");
                        continue;
                    }
                    
                    management.addStudent(id, name, grade);
                    break;
                    
                case 2:
                    System.out.print("Enter student ID to remove: ");
                    int idToRemove = 0;
                    try {
                        idToRemove = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    management.removeStudent(idToRemove);
                    break;
                    
                case 3:
                    System.out.print("Enter student ID to search: ");
                    int idToSearch = 0;
                    try {
                        idToSearch = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    
                    Student found = management.searchStudent(idToSearch);
                    if (found != null) {
                        System.out.println("Student found: " + found);
                    } else {
                        System.out.println("Student not found with ID " + idToSearch);
                    }
                    break;
                    
                case 4:
                    management.displayAllStudents();
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("Exiting Student Management System.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}

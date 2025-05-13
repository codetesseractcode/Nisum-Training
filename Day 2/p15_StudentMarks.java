/*
 15. Write a java Program to display Colleges and student marks info where each subject marks 
     should not be visible to outside (ex: take 3 subjects marks)
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class p15_StudentMarks 
{
    public static void main(String[] args) 
    {
        System.out.println("=== College and Student Marks Information System ===\n");
          // Create colleges
        College iitd = new College("IITD", "Indian Institute of Technology Delhi");
        College nitk = new College("NITK", "National Institute of Technology Karnataka");
        
        // Create students for IIT Delhi
        Student student1 = new Student("Rahul Sharma", "S001", iitd);
        student1.setMarks("Mathematics", 92);
        student1.setMarks("Physics", 88);
        student1.setMarks("Computer Science", 95);
        
        Student student2 = new Student("Priya Patel", "S002", iitd);
        student2.setMarks("Mathematics", 85);
        student2.setMarks("Physics", 91);
        student2.setMarks("Computer Science", 89);
        
        // Create students for NIT Karnataka
        Student student3 = new Student("Akash Kumar", "S003", nitk);
        student3.setMarks("Mathematics", 78);
        student3.setMarks("Physics", 82);
        student3.setMarks("Computer Science", 94);
          Student student4 = new Student("Divya Reddy", "S004", nitk);
        student4.setMarks("Mathematics", 93);
        student4.setMarks("Physics", 87);
        student4.setMarks("Computer Science", 90);
        
        // Add students to colleges
        iitd.addStudent(student1);
        iitd.addStudent(student2);
        nitk.addStudent(student3);
        nitk.addStudent(student4);
        
        // Display college information
        System.out.println("College Information:");
        iitd.displayCollegeInfo();
        System.out.println();
        nitk.displayCollegeInfo();
        
        // Display student information
        System.out.println("\nStudent Information:");
        student1.displayStudentInfo();
        System.out.println();
        student2.displayStudentInfo();
        System.out.println();
        student3.displayStudentInfo();
        System.out.println();
        student4.displayStudentInfo();
        
        // Try to access marks directly - This would cause a compilation error if uncommented
        // System.out.println(student1.subjectMarks.get("Mathematics"));  // Not accessible
        
        // We cannot access individual subject marks, but we can get the average
        System.out.println("\nStudent Averages:");
        System.out.println(student1.getName() + "'s average: " + student1.getAverageMarks());
        System.out.println(student2.getName() + "'s average: " + student2.getAverageMarks());
        System.out.println(student3.getName() + "'s average: " + student3.getAverageMarks());
        System.out.println(student4.getName() + "'s average: " + student4.getAverageMarks());
          // Highest scorer in each college
        System.out.println("\nHighest Scorers:");
        Student iitdTopper = iitd.getHighestScoringStudent();
        Student nitkTopper = nitk.getHighestScoringStudent();
        
        System.out.println("IIT Delhi topper: " + iitdTopper.getName() + " with average " + 
                           iitdTopper.getAverageMarks());
        System.out.println("NIT Karnataka topper: " + nitkTopper.getName() + " with average " + 
                           nitkTopper.getAverageMarks());
    }
}

class College 
{
    private String code;
    private String name;
    private List<Student> students;
    
    public College(String code, String name) 
    {
        this.code = code;
        this.name = name;
        this.students = new ArrayList<>();
    }
    
    public String getCode() 
    {
        return code;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void addStudent(Student student) 
    {
        students.add(student);
    }
    
    public void displayCollegeInfo() 
    {
        System.out.println("College: " + name + " (" + code + ")");
        System.out.println("Number of Students: " + students.size());
    }
    
    public Student getHighestScoringStudent() 
    {
        if (students.isEmpty()) 
        {
            return null;
        }
        
        Student highestScorer = students.get(0);
        double highestAverage = highestScorer.getAverageMarks();
        
        for (Student student : students) 
        {
            double average = student.getAverageMarks();
            if (average > highestAverage) 
            {
                highestAverage = average;
                highestScorer = student;
            }
        }
        
        return highestScorer;
    }
}

class Student 
{
    private String name;
    private String id;
    private College college;
    
    // Private map to store subject marks - not accessible from outside
    private Map<String, Integer> subjectMarks;
    
    public Student(String name, String id, College college) 
    {
        this.name = name;
        this.id = id;
        this.college = college;
        this.subjectMarks = new HashMap<>();
    }
    
    public String getName() 
    {
        return name;
    }
    
    public String getId() 
    {
        return id;
    }
    
    public College getCollege() 
    {
        return college;
    }
    
    // Method to set marks for a subject
    public void setMarks(String subject, int marks) 
    {
        if (marks >= 0 && marks <= 100) 
        {
            subjectMarks.put(subject, marks);
        } 
        else 
        {
            System.out.println("Invalid marks. Marks should be between 0 and 100.");
        }
    }
    
    // Method to get average marks
    public double getAverageMarks() 
    {
        if (subjectMarks.isEmpty()) 
        {
            return 0.0;
        }
        
        int total = 0;
        for (int marks : subjectMarks.values()) 
        {
            total += marks;
        }
        
        return (double) total / subjectMarks.size();
    }
    
    // Method to display student info including marks summary (but not individual marks)
    public void displayStudentInfo() 
    {
        System.out.println("Student: " + name + " (ID: " + id + ")");
        System.out.println("College: " + college.getName() + " (" + college.getCode() + ")");
        System.out.println("Number of subjects: " + subjectMarks.size());
        System.out.println("Average marks: " + String.format("%.2f", getAverageMarks()));
        
        // Grade calculation
        double avg = getAverageMarks();
        String grade;
        
        if (avg >= 90) 
        {
            grade = "A";
        } 
        else if (avg >= 80) 
        {
            grade = "B";
        } 
        else if (avg >= 70) 
        {
            grade = "C";
        } 
        else if (avg >= 60) 
        {
            grade = "D";
        } 
        else 
        {
            grade = "F";
        }
        
        System.out.println("Overall grade: " + grade);
        
        // Note that we don't expose individual subject marks
    }
}

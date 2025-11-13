package com.student.management;

import com.student.management.config.AppConfig;
import com.student.management.dao.StudentDAO;
import com.student.management.model.Course;
import com.student.management.model.Student;
import com.student.management.service.FeeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * MainApp - Main Application Class
 * Demonstrates: Complete integration of Spring + Hibernate with console-based interface
 * Features: Menu-driven operations for student and course management
 */
public class MainApp {

    private static AppConfig appConfig;
    private static StudentDAO studentDAO;
    private static FeeService feeService;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    ONLINE STUDENT MANAGEMENT SYSTEM");
        System.out.println("    Spring + Hibernate Mini Project");
        System.out.println("=".repeat(60));
        
        try {
            // Initialize Spring configuration and beans
            initializeBeans();
            
            scanner = new Scanner(System.in);
            boolean exit = false;
            
            while (!exit) {
                displayMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        addStudent();
                        break;
                    case 3:
                        viewAllStudents();
                        break;
                    case 4:
                        viewAllCourses();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        deleteStudent();
                        break;
                    case 7:
                        processPayment();
                        break;
                    case 8:
                        processRefund();
                        break;
                    case 9:
                        assignCourseToStudent();
                        break;
                    case 10:
                        exit = true;
                        System.out.println("\nThank you for using Student Management System!");
                        break;
                    default:
                        System.out.println("\nInvalid choice! Please try again.");
                }
            }
            
        } catch (Exception e) {
            System.err.println("\nApplication Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("\nApplication shutting down...");
        }
    }

    /**
     * Initialize Spring beans (Dependency Injection)
     */
    private static void initializeBeans() {
        System.out.println("\nInitializing Spring Beans...");
        appConfig = new AppConfig();
        studentDAO = appConfig.studentDAO();
        feeService = appConfig.feeService();
        System.out.println("Beans initialized successfully!\n");
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(60));
        System.out.println("1.  Add New Course");
        System.out.println("2.  Add New Student");
        System.out.println("3.  View All Students");
        System.out.println("4.  View All Courses");
        System.out.println("5.  Update Student");
        System.out.println("6.  Delete Student");
        System.out.println("7.  Process Fee Payment");
        System.out.println("8.  Process Fee Refund");
        System.out.println("9.  Assign Course to Student");
        System.out.println("10. Exit");
        System.out.println("-".repeat(60));
        System.out.print("Enter your choice: ");
    }

    /**
     * Get user's menu choice
     */
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }

    /**
     * Add a new course
     */
    private static void addCourse() {
        System.out.println("\n--- ADD NEW COURSE ---");
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        
        System.out.print("Enter duration (in months): ");
        int duration = scanner.nextInt();
        
        Course course = new Course(courseName, duration);
        studentDAO.addCourse(course);
        System.out.println("Course added successfully!");
    }

    /**
     * Add a new student
     */
    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter initial balance: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine());
        
        Student student = new Student(name, null, balance);
        studentDAO.addStudent(student);
        System.out.println("Student added successfully!");
    }

    /**
     * View all students
     */
    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students == null || students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println(String.format("%-5s %-20s %-20s %-15s", 
            "ID", "Name", "Course", "Balance"));
        System.out.println("-".repeat(60));
        
        for (Student student : students) {
            System.out.println(String.format("%-5d %-20s %-20s $%-14.2f",
                student.getStudentId(),
                student.getName(),
                student.getCourse() != null ? student.getCourse().getCourseName() : "None",
                student.getBalance()));
        }
    }

    /**
     * View all courses
     */
    private static void viewAllCourses() {
        System.out.println("\n--- ALL COURSES ---");
        List<Course> courses = studentDAO.getAllCourses();
        
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.println(String.format("%-5s %-30s %-15s", 
            "ID", "Course Name", "Duration"));
        System.out.println("-".repeat(50));
        
        for (Course course : courses) {
            System.out.println(String.format("%-5d %-30s %-15s",
                course.getCourseId(),
                course.getCourseName(),
                course.getDuration() + " months"));
        }
    }

    /**
     * Update student details
     */
    private static void updateStudent() {
        System.out.println("\n--- UPDATE STUDENT ---");
        
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        scanner.nextLine(); // Clear buffer
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Current details: " + student);
        System.out.print("Enter new name (or press Enter to keep current): ");
        String newName = scanner.nextLine();
        
        if (!newName.trim().isEmpty()) {
            student.setName(newName);
        }
        
        studentDAO.updateStudent(student);
    }

    /**
     * Delete a student
     */
    private static void deleteStudent() {
        System.out.println("\n--- DELETE STUDENT ---");
        
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        
        studentDAO.deleteStudent(studentId);
    }

    /**
     * Process fee payment (demonstrates transaction management)
     */
    private static void processPayment() {
        System.out.println("\n--- PROCESS FEE PAYMENT ---");
        
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        
        System.out.print("Enter payment amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        
        boolean success = feeService.processPayment(studentId, amount);
        if (!success) {
            System.out.println("Payment failed! Please check the details.");
        }
    }

    /**
     * Process fee refund (demonstrates transaction management)
     */
    private static void processRefund() {
        System.out.println("\n--- PROCESS FEE REFUND ---");
        
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        
        System.out.print("Enter refund amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        
        boolean success = feeService.processRefund(studentId, amount);
        if (!success) {
            System.out.println("Refund failed! Please check the details.");
        }
    }

    /**
     * Assign course to student
     */
    private static void assignCourseToStudent() {
        System.out.println("\n--- ASSIGN COURSE TO STUDENT ---");
        
        System.out.print("Enter student ID: ");
        Long studentId = scanner.nextLong();
        
        System.out.print("Enter course ID: ");
        Long courseId = scanner.nextLong();
        
        Student student = studentDAO.getStudentById(studentId);
        Course course = studentDAO.getCourseById(courseId);
        
        if (student == null || course == null) {
            System.out.println("Student or Course not found!");
            return;
        }
        
        student.setCourse(course);
        studentDAO.updateStudent(student);
        System.out.println("Course assigned successfully!");
    }
}

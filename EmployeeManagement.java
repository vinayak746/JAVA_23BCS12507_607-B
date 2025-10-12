import java.io.*;
import java.util.*;

class Employee implements Serializable {
    int id;
    String name, designation;
    double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Designation: " + designation + " | Salary: " + salary;
    }
}

public class EmployeeManagement {
    static final String FILE_NAME = "employees.dat";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addEmployee(); break;
                case 2: displayEmployees(); break;
                case 3: System.out.println("Exiting."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();

        Employee emp = new Employee(id, name, designation, salary);

        ArrayList<Employee> employees = readEmployees();
        employees.add(emp);
        writeEmployees(employees);
        System.out.println("Employee added!");
    }

    static void displayEmployees() {
        ArrayList<Employee> employees = readEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
        } else {
            System.out.println("Employee Records:");
            for (Employee e : employees) {
                System.out.println(e);
            }
        }
    }

    static ArrayList<Employee> readEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (ArrayList<Employee>) in.readObject();
        } catch (Exception e) {
            // If file not found, or empty, return empty list
        }
        return employees;
    }

    static void writeEmployees(ArrayList<Employee> employees) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

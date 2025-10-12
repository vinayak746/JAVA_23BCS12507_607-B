import java.io.*;
import java.util.Scanner;

class Student implements Serializable {
    int studentID;
    String name;
    double grade;

    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
}

public class Serialization {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Grade: ");
        double grade = scanner.nextDouble();

        Student student = new Student(id, name, grade);

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.txt"))) {
            out.writeObject(student);
            System.out.println("Student object serialized to student.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.txt"))) {
            Student s = (Student) in.readObject();
            System.out.println("Deserialized Student:");
            System.out.println("ID: " + s.studentID + ", Name: " + s.name + ", Grade: " + s.grade);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

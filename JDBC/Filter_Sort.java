import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    double marks;

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    public String toString() {
        return name + " (" + marks + "%)";
    }
}

public class Filter_Sort {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Arjun", 82.0),
            new Student("Bhavna", 76.5),
            new Student("Chirag", 71.0),
            new Student("Deepa", 89.0),
            new Student("Ankit", 69.5)
        );

        // Filter students with marks > 75%, sort by marks, and display names
        List<String> topStudents = students.stream()
            .filter(s -> s.marks > 75)
            .sorted(Comparator.comparingDouble(s -> s.marks))
            .map(s -> s.name)
            .collect(Collectors.toList());

        System.out.println("Students scoring above 75% (sorted by marks):");
        topStudents.forEach(System.out::println);
    }
}
